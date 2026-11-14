package com.example.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etTitle,etAuthor,etTotalPages,etReadPages,etDate,etPdfUrl,etContent;
    RadioGroup rgCategory;
    Button btnSave,btnViewList;
    TextView tvRating;
    SeekBar sbRating;
    DBHelper db;

    int selectedRating=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etTitle=findViewById(R.id.etTitle);
        etAuthor=findViewById(R.id.etAuthor);
        etTotalPages=findViewById(R.id.etTotalPages);
        etReadPages=findViewById(R.id.etReadPages);
        etDate=findViewById(R.id.etDate);
        etPdfUrl=findViewById(R.id.etPdfUrl);
        etContent=findViewById(R.id.etContent);
        rgCategory=findViewById(R.id.rgCategory);
        btnSave=findViewById(R.id.btnSave);
        btnViewList=findViewById(R.id.btnViewList);
        tvRating=findViewById(R.id.tvRating);
        sbRating=findViewById(R.id.sbRating);

        db=new DBHelper(this);


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                                String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                                etDate.setText(formattedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser) {
                selectedRating = progress;
                tvRating.setText("Rating:" + progress + " /5");

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String title=etTitle.getText().toString();
                String author=etAuthor.getText().toString();
                String content=etContent.getText().toString();
                String pdfurl=etPdfUrl.getText().toString();
                String totalStr=etTotalPages.getText().toString();
                String readStr=etReadPages.getText().toString();
                String date=etDate.getText().toString();

                if(title.isEmpty() || author.isEmpty() || content.isEmpty() || totalStr.isEmpty() || pdfurl.isEmpty() || readStr.isEmpty() || date.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedRadioId=rgCategory.getCheckedRadioButtonId();
                if(selectedRadioId==-1)
                {
                    Toast.makeText(MainActivity.this,"Please select a category",Toast.LENGTH_SHORT).show();
                    return;
                }
                String category="";
                if(selectedRadioId==R.id.rbBook)
                {
                    category="Book";
                } else if (selectedRadioId==R.id.rbArticle) {
                    category="Research Article";
                }
                int totalPages=Integer.parseInt(totalStr);
                int readPages=Integer.parseInt(readStr);

                if(totalPages<=0 || readPages>totalPages)
                {
                    Toast.makeText(MainActivity.this,"Read pages cannot exceed total pages",Toast.LENGTH_SHORT).show();
                    return;
                }
                double progress=((double)readPages/totalPages)*100.0;
                boolean isInserted = db.insertBook(title, author, category, content, pdfurl, totalPages, readPages, progress, selectedRating, date);
                if(isInserted)
                {
                    String formattedProgress=String.format("%.1f",progress);
                    Toast.makeText(MainActivity.this,"Saved progress: "+formattedProgress+"%",Toast.LENGTH_LONG).show();

                    etTitle.setText("");
                    etAuthor.setText("");
                    etTotalPages.setText("");
                    etReadPages.setText("");
                    etDate.setText("");
                    etPdfUrl.setText("");
                    etContent.setText("");
                    rgCategory.clearCheck();
                    sbRating.setProgress(0);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to save progress",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBooksActivity.class);
                startActivity(intent);
            }
        });
    }
}