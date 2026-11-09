package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "ReadTrack.db";
    public DBHelper(Context context) {
         super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE books(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "category TEXT, " +
                "totalPages INTEGER, " +
                "readPages INTEGER, " +
                "progress DOUBLE, " +
                "rating INTEGER,"+
                "addedDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        onCreate(sqLiteDatabase);
    }

    public long insertBook(String title,String author,String category,int totalPages,int readPages,double progress,int rating,String addedDate)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",title);
        cv.put("author",author);
        cv.put("category",category);
        cv.put("totalPages",totalPages);
        cv.put("readPages",readPages);
        cv.put("progress",progress);
        cv.put("rating",rating);
        cv.put("addedDate",addedDate);
        return sqLiteDatabase.insert("books",null,cv);
    }
    public Cursor getAllBooks(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM books",null);
    }
    public boolean updateBook(int id, String title, String author, String category, int totalPages, int readPages, double progress, int rating, String addedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("author", author);
        cv.put("category", category);
        cv.put("totalPages", totalPages);
        cv.put("readPages", readPages);
        cv.put("progress", progress);
        cv.put("rating", rating);
        cv.put("addedDate", addedDate);
        int result = db.update("books", cv, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("books", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
