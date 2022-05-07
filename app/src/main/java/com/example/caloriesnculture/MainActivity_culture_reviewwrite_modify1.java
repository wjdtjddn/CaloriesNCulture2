package com.example.caloriesnculture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MainActivity_culture_reviewwrite_modify1 extends AppCompatActivity {
    EditText txt_culture_reviewmo1_title,txt_culture_reviewmo1_author,txt_culture_reviewmo1_content;//이름 사람 내용
    Button btn_culture_reviewmo1_update,btn_culture_reviewmo1_delete,btn_culture_reviewmo1_genre,btn_culture_reviewmo1_cancel;
    TextView txt_culture_reviewmo1_genre,txt_culture_review1_year,txt_culture_review1_month,txt_culture_review1_day;
    String primarynumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_reviewwrite_modify1);

        Intent intent = getIntent();
        final String mno = intent.getExtras().getString("mno");
        culture01_01RegisterActivity_review task = new culture01_01RegisterActivity_review();
        try{

            txt_culture_reviewmo1_title = findViewById(R.id.txt_culture_reviewmo1_title);
            txt_culture_reviewmo1_title.setInputType ( InputType. TYPE_TEXT_FLAG_NO_SUGGESTIONS );
            txt_culture_reviewmo1_author = findViewById(R.id.txt_culture_reviewmo1_author);
            txt_culture_reviewmo1_author.setInputType ( InputType. TYPE_TEXT_FLAG_NO_SUGGESTIONS );
            txt_culture_reviewmo1_content = findViewById(R.id.txt_culture_reviewmo1_content);
            txt_culture_reviewmo1_content.setInputType ( InputType. TYPE_TEXT_FLAG_NO_SUGGESTIONS );
            btn_culture_reviewmo1_genre = findViewById(R.id.btn_culture_reviewmo1_genre);
            txt_culture_reviewmo1_genre = findViewById(R.id.txt_culture_reviewmo1_genre);

            btn_culture_reviewmo1_genre.setOnClickListener(new View.OnClickListener() {//장르
                @Override
                public void onClick(View v) {//장르 선택 이동버튼
                    Intent intent = new Intent(MainActivity_culture_reviewwrite_modify1.this, MainActivity_culture_selectDis1.class);
                    startActivityForResult(intent, 0);//액티비티 이동

                }
            });


            txt_culture_reviewmo1_content = findViewById(R.id.txt_culture_reviewmo1_content);
            txt_culture_reviewmo1_content.setInputType ( InputType. TYPE_TEXT_FLAG_NO_SUGGESTIONS );
            btn_culture_reviewmo1_update = findViewById(R.id.btn_culture_reviewmo1_update);//업데이트 버튼 선언
            //btn_culture_reviewmo1_delete = findViewById(R.id.btn_culture_reviewmo1_delete);//삭제 버튼 선언

            SharedPreferences pref2 = getSharedPreferences("staticFILE", MODE_PRIVATE);
            String id = pref2.getString("nickname",null);
            String result = task.execute(mno,id).get();
            result = result.trim();//공백제거
            result = result.substring(1);//문자열 자르기
            result = result.substring(0, result.length()-1);

            System.out.println("arrar " + result);

            String[] array = result.split(", ");//문자열 자르기기
            txt_culture_reviewmo1_title.setText(array[0]);
            txt_culture_reviewmo1_author.setText(array[1]);
            txt_culture_reviewmo1_genre.setText(array[2]);
            txt_culture_reviewmo1_content.setText(array[3]);
            primarynumber = array[5];

            String[] dateString = (array[4].split(" "))[0].split("-");
            txt_culture_review1_year = findViewById(R.id.txt_culture_review1_year);
            txt_culture_review1_month = findViewById(R.id.txt_culture_review1_month);
            txt_culture_review1_day = findViewById(R.id.txt_culture_review1_day);

            txt_culture_review1_year.setText(dateString[0]+"년");
            txt_culture_review1_month.setText(dateString[1]+"월");
            txt_culture_review1_day.setText(dateString[2]+"일");

        }catch(Exception e){
            e.printStackTrace();
        }

        btn_culture_reviewmo1_update.setOnClickListener(new View.OnClickListener() {//수정버튼
            @Override
            public void onClick(View v) {//액티비티 이동,근데 업데이트


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_culture_reviewwrite_modify1.this);

                builder.setTitle("경 고 창").setMessage("정말 바꾸시겠습니까?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        culture01_01RegisterActivity_update update = new culture01_01RegisterActivity_update();
                        String mname = txt_culture_reviewmo1_title.getText().toString();
                        String mpeople = txt_culture_reviewmo1_author.getText().toString();
                        String mgenre = txt_culture_reviewmo1_genre.getText().toString();
                        String mreview = txt_culture_reviewmo1_content.getText().toString();
                        System.out.println("mno"+mno);
                        System.out.println("mname"+mname);
                        System.out.println("mpeople"+mpeople);
                        System.out.println("mgenre"+mgenre);
                        System.out.println("mreview"+mreview);

                        try {
                            update.execute(mno,mname,mpeople,mgenre,mreview,primarynumber).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                        Intent up_in = new Intent(MainActivity_culture_reviewwrite_modify1.this, MainActivity_culture_categorymain1.class);
                        up_in.putExtra("mno", mno);
                        startActivity(up_in);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                System.out.println("눌렸냐");
            }
        });
        btn_culture_reviewmo1_cancel = findViewById(R.id.btn_culture_reviewmo1_cancel);
        btn_culture_reviewmo1_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_culture_reviewmo1_cancel = new Intent(MainActivity_culture_reviewwrite_modify1.this,MainActivity_culture_categorymain1.class);
                startActivity(btn_culture_reviewmo1_cancel);
            }
        });

//        btn_culture_reviewmo1_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//삭제버튼
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_culture_reviewwrite_modify1.this);
//
//                builder.setTitle("경 고 창").setMessage("정말 삭제할거야?");
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int id)
//                    {
//                        culture01_01RegisterActivity_delete delete = new culture01_01RegisterActivity_delete();
//                        try {
//                            delete.execute(mno).get();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        finish();
//                        Intent de_in = new Intent(MainActivity_culture_reviewwrite_modify1.this, MainActivity_culture_categorymain1.class);
//                        de_in.putExtra("mno", mno);
//                        startActivity(de_in);
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int id)
//                    {
//                        Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//                System.out.println("눌렸냐!!!");
//            }
//        });
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if(resultCode == RESULT_OK){
            String result_genre = data.getStringExtra("sendData");//데이터 담는 코드
            txt_culture_reviewmo1_genre.setText(result_genre);
        }
    }
}