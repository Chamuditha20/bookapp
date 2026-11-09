package com.example.myapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

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
    }
}