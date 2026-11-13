package com.example.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateDeleteActivity extends AppCompatActivity {

    EditText etUpTitle, etUpAuthor, etUpContent, etUpPdfUrl, etUpTotalPages, etUpReadPages, etUpDate;
    RadioGroup rgUpCategory;
    RadioButton rbUpBook, rbUpArticle;
    SeekBar sbUpRating;
    TextView tvUpRating;
    Button btnOpenPdf, btnUpdate, btnDelete, btnBackToList;

    DBHelper db;
    int bookId = -1;
    int selectedRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        etUpTitle = findViewById(R.id.etUpTitle);
        etUpAuthor = findViewById(R.id.etUpAuthor);
        etUpContent = findViewById(R.id.etUpContent);
        etUpPdfUrl = findViewById(R.id.etUpPdfUrl);
        etUpTotalPages = findViewById(R.id.etUpTotalPages);
        etUpReadPages = findViewById(R.id.etUpReadPages);
        etUpDate = findViewById(R.id.etUpDate);
        rgUpCategory = findViewById(R.id.rgUpCategory);
        rbUpBook = findViewById(R.id.rbUpBook);
        rbUpArticle = findViewById(R.id.rbUpArticle);
        sbUpRating = findViewById(R.id.sbUpRating);
        tvUpRating = findViewById(R.id.tvUpRating);
        btnOpenPdf = findViewById(R.id.btnOpenPdf);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnBackToList = findViewById(R.id.btnBackToList);

        db = new DBHelper(this);

        bookId = getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String category = getIntent().getStringExtra("category");
        String content = getIntent().getStringExtra("content");
        String pdfUrl = getIntent().getStringExtra("pdfUrl");
        int totalPages = getIntent().getIntExtra("totalPages", 0);
        int readPages = getIntent().getIntExtra("readPages", 0);
        selectedRating = getIntent().getIntExtra("rating", 0);
        String addedDate = getIntent().getStringExtra("addedDate");

        etUpTitle.setText(title);
        etUpAuthor.setText(author);
        etUpContent.setText(content);
        etUpPdfUrl.setText(pdfUrl);
        etUpTotalPages.setText(String.valueOf(totalPages));
        etUpReadPages.setText(String.valueOf(readPages));
        etUpDate.setText(addedDate);

        if ("Book".equals(category)) {
            rbUpBook.setChecked(true);
        } else if ("Research Article".equals(category)) {
            rbUpArticle.setChecked(true);
        }

        sbUpRating.setProgress(selectedRating);
        tvUpRating.setText("Rating: " + selectedRating + " / 5");

        sbUpRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedRating = progress;
                tvUpRating.setText("Rating: " + progress + " / 5");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnOpenPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdfUrl = etUpPdfUrl.getText().toString().trim();
                if (pdfUrl.isEmpty()) {
                    Toast.makeText(UpdateDeleteActivity.this, "No PDF Link available for this item!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                startActivity(browserIntent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etUpTitle.getText().toString();
                String author = etUpAuthor.getText().toString();
                String content = etUpContent.getText().toString();
                String pdfUrl = etUpPdfUrl.getText().toString();
                String totalStr = etUpTotalPages.getText().toString();
                String readStr = etUpReadPages.getText().toString();
                String date = etUpDate.getText().toString();

                if (title.isEmpty() || author.isEmpty() || content.isEmpty() || totalStr.isEmpty() || readStr.isEmpty() || date.isEmpty()) {
                    Toast.makeText(UpdateDeleteActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedRadioId = rgUpCategory.getCheckedRadioButtonId();
                String category = "";
                if (selectedRadioId == R.id.rbUpBook) {
                    category = "Book";
                } else if (selectedRadioId == R.id.rbUpArticle) {
                    category = "Research Article";
                }

                int totalPages = Integer.parseInt(totalStr);
                int readPages = Integer.parseInt(readStr);

                if (totalPages <= 0 || readPages > totalPages) {
                    Toast.makeText(UpdateDeleteActivity.this, "Read pages cannot exceed total pages!", Toast.LENGTH_SHORT).show();
                    return;
                }


                double progress = ((double) readPages / totalPages) * 100.0;

                boolean isUpdated = db.updateBook(bookId, title, author, category, totalPages, readPages, progress, selectedRating, date, content, pdfUrl);

                if (isUpdated) {
                    String formattedProgress = String.format("%.1f", progress);
                    Toast.makeText(UpdateDeleteActivity.this, "Successfully Updated! Progress: " + formattedProgress + "%", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = db.deleteBook(bookId);

                if (isDeleted) {
                    Toast.makeText(UpdateDeleteActivity.this, "Record Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}