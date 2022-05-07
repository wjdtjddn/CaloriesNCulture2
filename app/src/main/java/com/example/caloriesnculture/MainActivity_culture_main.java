package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_culture_main extends AppCompatActivity {


    TextView nickname;
    String nickdata="";
    SharedPreferences pref;

    @Override
    public void onBackPressed() {
        Intent intent_btn_culture_main_back =new Intent(MainActivity_culture_main.this , MainActivity_main.class);
        startActivity(intent_btn_culture_main_back);
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_main);

        nickname=(TextView)findViewById(R.id.txt_culture_main_name);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        nickname.setText(nickdata);



        Button btn_culrue_main_movie = findViewById(R.id.btn_culrue_main_movie);

        btn_culrue_main_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culrue_main_movie =new Intent(MainActivity_culture_main.this , MainActivity_culture_categorymain1.class);
                startActivity(intent_btn_culrue_main_movie);
                finish();
            }
        });
        Button btn_culrue_main_book = findViewById(R.id.btn_culrue_main_book);

        btn_culrue_main_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culrue_main_book =new Intent(MainActivity_culture_main.this , MainActivity_culture_categorymain2.class);
                startActivity(intent_btn_culrue_main_book);
                finish();
            }
        });
        Button btn_culrue_main_trip = findViewById(R.id.btn_culrue_main_trip);

        btn_culrue_main_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culrue_main_trip =new Intent(MainActivity_culture_main.this , MainActivity_culture_categorymain3.class);
                startActivity(intent_btn_culrue_main_trip);
                finish();
            }
        });
        Button btn_culrue_main_diary = findViewById(R.id.btn_culrue_main_diary);

        btn_culrue_main_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culrue_main_diary =new Intent(MainActivity_culture_main.this , MainActivity_culture_categorymain4.class);
                startActivity(intent_btn_culrue_main_diary);
                finish();
            }
        });

    }
}
