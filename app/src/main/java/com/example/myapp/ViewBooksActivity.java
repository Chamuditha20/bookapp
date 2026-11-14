package com.example.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewBooksActivity extends AppCompatActivity {

    RecyclerView recyclerViewBooks;
    TextView tvSummaryStats;
    EditText etSearchBook;
    Button btnBack;

    DBHelper db;
    ArrayList<BookData> bookList;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);


        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        tvSummaryStats = findViewById(R.id.tvSummaryStats);
        etSearchBook = findViewById(R.id.etSearchBook);
        btnBack = findViewById(R.id.btnBack);

        db = new DBHelper(this);
        bookList = new ArrayList<>();


        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));


        loadDataFromDatabase();


        etSearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void loadDataFromDatabase() {
        bookList.clear();
        Cursor cursor = db.getAllBooks();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String category = cursor.getString(3);
                String content = cursor.getString(4);
                String pdfUrl = cursor.getString(5);
                int totalPages = cursor.getInt(6);
                int readPages = cursor.getInt(7);
                double progress = cursor.getDouble(8);
                int rating = cursor.getInt(9);
                String addedDate = cursor.getString(10);


                BookData book = new BookData(id, title, author, category, content, pdfUrl, totalPages, readPages, progress, rating, addedDate);
                bookList.add(book);
            }
            cursor.close();
        }


        adapter = new BookAdapter(this, bookList);
        recyclerViewBooks.setAdapter(adapter);


        updateSummaryStats();
    }


    private void updateSummaryStats() {
        int totalItems = bookList.size();
        int completedItems = 0;

        for (BookData book : bookList) {
            if (book.getProgress() >= 100.0) {
                completedItems++;
            }
        }

        tvSummaryStats.setText("Total Items: " + totalItems + "   |   Completed: " + completedItems);
    }


    private void filterBooks(String text) {
        ArrayList<BookData> filteredList = new ArrayList<>();
        String query = text.toLowerCase().trim();
        for (BookData item : bookList) {
            if (item.getTitle().toLowerCase().contains(query) ||
                item.getAuthor().toLowerCase().contains(query) ||
                item.getCategory().toLowerCase().contains(query)) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDatabase();
    }
}