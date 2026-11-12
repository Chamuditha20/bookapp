package com.example.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewBooksActivity extends AppCompatActivity {

    RecyclerView recyclerViewBooks;
    Button btnBack;
    DBHelper db;
    ArrayList<BookData> bookList;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);


        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        btnBack = findViewById(R.id.btnBack);
        db = new DBHelper(this);
        bookList = new ArrayList<>();


        loadBooksData();


        adapter = new BookAdapter(this, bookList);
        recyclerViewBooks.setHasFixedSize(true);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void loadBooksData() {
        Cursor res = db.getAllBooks();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No books or articles found!", Toast.LENGTH_SHORT).show();
            return;
        }

        while (res.moveToNext()) {

            int id = res.getInt(0);
            String title = res.getString(1);
            String author = res.getString(2);
            String category = res.getString(3);
            String content = res.getString(4);
            String pdfUrl = res.getString(5);
            int totalPages = res.getInt(6);
            int readPages = res.getInt(7);
            double progress = res.getDouble(8);
            int rating = res.getInt(9);
            String addedDate = res.getString(10);


            bookList.add(new BookData(id, title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate));
        }
    }
}