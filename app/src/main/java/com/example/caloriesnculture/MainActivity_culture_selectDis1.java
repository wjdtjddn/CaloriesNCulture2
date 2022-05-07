package com.example.caloriesnculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_culture_selectDis1 extends AppCompatActivity {

    public static final String RESULT_DATA = "ResultData";
    private RadioGroup rg_select_genre;
    private RadioButton rb_select_genre_1,rb_select_genre_4,rb_select_genre_3,rb_select_genre_5,rb_select_genre_2,rb_select_genre_6;
    Button btn_culture_select_genre;
    private String str_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_select_dis1);
        rg_select_genre = (RadioGroup)findViewById(R.id.rg_select_genre);
        rb_select_genre_1 = findViewById(R.id.rb_select_genre_1);
        rb_select_genre_4 = findViewById(R.id.rb_select_genre_4);
        rb_select_genre_3 = findViewById(R.id.rb_select_genre_3);
        rb_select_genre_5 = findViewById(R.id.rb_select_genre_5);
        rb_select_genre_2 = findViewById(R.id.rb_select_genre_2);
        rb_select_genre_6 = findViewById(R.id.rb_select_genre_6);
        btn_culture_select_genre = findViewById(R.id.btn_culture_select_genre);

        rg_select_genre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                System.out.println("i : " + i);
                if(i == R.id.rb_select_genre_1){//로맨스
                    Toast.makeText(MainActivity_culture_selectDis1.this,"로맨스 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_1.getText().toString();
                    System.out.println("genre_1 선택됨");
                }
                else if(i == R.id.rb_select_genre_4){//공상과학
                    Toast.makeText(MainActivity_culture_selectDis1.this,"공상과학 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_4.getText().toString();
                    System.out.println("genre_4 선택됨");
                }
                else if(i == R.id.rb_select_genre_3){//스릴러
                    Toast.makeText(MainActivity_culture_selectDis1.this,"스릴러 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_3.getText().toString();
                    System.out.println("genre_3 선택됨");
                }
                else if(i == R.id.rb_select_genre_5){//코미디
                    Toast.makeText(MainActivity_culture_selectDis1.this,"코미디 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_5.getText().toString();
                    System.out.println("genre_5 선택됨");
                }
                else if(i == R.id.rb_select_genre_2){//액션
                    Toast.makeText(MainActivity_culture_selectDis1.this,"액션 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_2.getText().toString();
                    System.out.println("genre_2 선택됨");
                }
                else if(i == R.id.rb_select_genre_6){//판타지
                    Toast.makeText(MainActivity_culture_selectDis1.this,"판타지 선택됨",Toast.LENGTH_SHORT).show();
                    str_result = rb_select_genre_6.getText().toString();
                    System.out.println("genre_6 선택됨");
                }
            }});
        btn_culture_select_genre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//장르값 배송
                System.out.println("result : "+str_result);
                if(str_result != null){//str_result가 빈값이 아니라면
                    //Toast.makeText(MainActivity_culture_selectDis1.this,str_result,Toast.LENGTH_SHORT).show();
                    Intent intent01 = new Intent(MainActivity_culture_selectDis1.this, MainActivity_culture_reviewwrite1.class);//해당 클래스로 전송
                    //intent01.putExtra(RESULT_DATA,str_result);
                    intent01.putExtra(MainActivity_culture_reviewwrite1.SEND_DATA,str_result);
                    setResult(RESULT_OK,intent01);
                }else{//str_result가 빈값일 경우
                    Toast.makeText(MainActivity_culture_selectDis1.this,"장르를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}