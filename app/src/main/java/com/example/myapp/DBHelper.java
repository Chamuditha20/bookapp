package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

 
    private static final String DATABASE_NAME = "ReadTrack.db";
    private static final int DATABASE_VERSION = 4;

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


        String driveFolderUrl = "https://drive.google.com/drive/folders/1-Emz9XOdwwZ4lI83yZVoHg60qXJSpDUs?usp=sharing";


        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'A Very Busy Day!', 'Chisanga Mukuka', 'Book', " +
                "'A story about a busy trip to town with Mama, seeing tall buildings, shops, and choosing a special gift.', " +
                "'" + driveFolderUrl + "', " +
                "20, 10, 50.0, 5, '2026-11-15')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Auntie Boi’s Gift', 'Baeletsi Tsatsi', 'Book', " +
                "'Kopano and Rea travel to the big post office in town to collect a special surprise gift sent by Auntie Boi.', " +
                "'" + driveFolderUrl + "', " +
                "24, 24, 100.0, 5, '2026-11-15')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Mama Antelope’s House', 'Florenca Marundu', 'Book', " +
                "'Hare plays a trick by locking himself inside Mama Antelope’s house, and all the animals try to open the door.', " +
                "'" + driveFolderUrl + "', " +
                "22, 5, 22.7, 4, '2026-11-15')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Teju’s Shadow', 'Sope Martins', 'Book', " +
                "'Teju learns that his shadow is not scary at all and makes fun shadow puppets with Mum before bedtime.', " +
                "'" + driveFolderUrl + "', " +
                "20, 0, 0.0, 5, '2026-11-15')");


        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Attention Is All You Need', 'Ashish Vaswani et al.', 'Research Article', " +
                "'The foundational research paper introducing the Transformer architecture for Natural Language Processing and Deep Learning.', " +
                "'https://arxiv.org/pdf/1706.03762.pdf', " +
                "15, 15, 100.0, 5, '2026-11-05')");

        sqLiteDatabase.execSQL("INSERT INTO books (title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate) VALUES (" +
                "'Clean Code', 'Robert C. Martin', 'Book', " +
                "'A handbook of agile software craftsmanship. Focuses on writing readable, maintainable, and efficient code.', " +
                "'https://github.com/jnguyen095/clean-code/raw/master/Clean.Code.A.Handbook.of.Agile.Software.Craftsmanship.pdf', " +
                "464, 120, 25.8, 5, '2026-11-01')");
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
        return result != -1;
    }


    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM books ORDER BY id DESC", null);
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