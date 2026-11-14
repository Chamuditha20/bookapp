package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ReadTrack.db";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE books(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "category TEXT, " +
                "content TEXT, " +
                "pdfUrl TEXT, " +
                "totalPages INTEGER, " +
                "readPages INTEGER, " +
                "progress DOUBLE, " +
                "rating INTEGER, " +
                "addedDate TEXT)");


        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Clean Code', 'Robert C. Martin', 'Book', " +
                "'A handbook of agile software craftsmanship. Focuses on writing readable, maintainable, and efficient code.', " +
                "'https://github.com/jnguyen095/clean-code/raw/master/Clean.Code.A.Handbook.of.Agile.Software.Craftsmanship.pdf', " +
                "464, 120, 25.8, 5, '2026-11-01')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Attention Is All You Need', 'Ashish Vaswani et al.', 'Research Article', " +
                "'The foundational research paper introducing the Transformer architecture for Natural Language Processing and Deep Learning.', " +
                "'https://arxiv.org/pdf/1706.03762.pdf', " +
                "15, 15, 100.0, 5, '2026-11-05')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'The Pragmatic Programmer', 'Andrew Hunt & David Thomas', 'Book', " +
                "'Your journey to mastery. Covers core software engineering best practices, debugging, and career advice.', " +
                "'https://www.cin.ufpe.br/~cavmj/104The%20Pragmatic%20Programmer,%20From%20Journeyman%20To%20Master%20-%20Andrew%20Hunt,%20David%20Thomas%20-%20Addison%20Wesley%20-%201999.pdf', " +
                "352, 50, 14.2, 4, '2026-11-10')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        onCreate(sqLiteDatabase);
    }


    public boolean insertBook(String title, String author, String category, String content, String pdfUrl, int totalPages, int readPages, double progress, int rating, String addedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("author", author);
        values.put("category", category);
        values.put("content", content);
        values.put("pdfUrl", pdfUrl);
        values.put("totalPages", totalPages);
        values.put("readPages", readPages);
        values.put("progress", progress);
        values.put("rating", rating);
        values.put("addedDate", addedDate);

        long result = db.insert("books", null, values);
        return result != -1; //
    }


    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books ORDER BY id DESC", null);
        return cursor;
    }


    public boolean updateBook(int id, String title, String author, String category, String content, String pdfUrl, int totalPages, int readPages, double progress, int rating, String addedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("author", author);
        values.put("category", category);
        values.put("content", content);
        values.put("pdfUrl", pdfUrl);
        values.put("totalPages", totalPages);
        values.put("readPages", readPages);
        values.put("progress", progress);
        values.put("rating", rating);
        values.put("addedDate", addedDate);

        long result = db.update("books", values, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

   
    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("books", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}