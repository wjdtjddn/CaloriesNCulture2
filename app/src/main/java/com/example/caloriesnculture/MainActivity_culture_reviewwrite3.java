package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

public class MainActivity_culture_reviewwrite3 extends AppCompatActivity {

    TextView date; String day="";
    String nickdata = "";
    SharedPreferences pref;
    Button btn_culture_review3_save;
    EditText txt_culture_review3_title,txt_culture_review3_content;


    ArrayAdapter<CharSequence> adspin1;
    Spinner sp; String text="";//년도변수

    ArrayAdapter<CharSequence> adspin2;
    Spinner sp2; String text2="";//년도변수
    EditText editText_select_trip2;
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_culture_reviewwrite3.this, MainActivity_culture_categorymain3.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_reviewwrite3);

        editText_select_trip2=(EditText)findViewById(R.id.editText_select_trip2);

        sp2 = (Spinner)findViewById(R.id.spinner_select_dis3_trip2);
        adspin2 = ArrayAdapter.createFromResource(this, R.array.county1, android.R.layout.simple_spinner_dropdown_item);

        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adspin2);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text2=adspin2.getItem(position).toString();
                Toast.makeText(MainActivity_culture_reviewwrite3.this, text2, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        sp = (Spinner)findViewById(R.id.spinner_culture_review3_author);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text=adspin1.getItem(position).toString();
                Toast.makeText(MainActivity_culture_reviewwrite3.this, text, Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        date=(TextView)findViewById(R.id.txt_culture_review3_date);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_culture","error");
        date.setText(day);
        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");




        btn_culture_review3_save = findViewById(R.id.btn_culture_review3_save);
        btn_culture_review3_save.setOnClickListener(btnListener1);

        txt_culture_review3_title=findViewById(R.id.txt_culture_review3_title);


        txt_culture_review3_content=findViewById(R.id.txt_culture_review3_content);

    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/culture3_write.jsp");//바꿔주세요//"http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //nickdata,day,"3",title,author,genre,content,"save"
                sendMsg1 = "nickname="+strings[0]+"&day="+strings[1]+"&category="+strings[2]+"&title="+strings[3]+"&weather="+strings[4]+"&place="+strings[5]+"&content="+strings[6]+"&type="+strings[7];
                osw.write(sendMsg1);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg1 = buffer.toString();


                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg1;
        }
    }

    View.OnClickListener btnListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view1) {

            switch (view1.getId()) {

                case R.id.btn_culture_review3_save:
                    //컬쳐번호,프라이머리넘버, 날짜, 카테고리넘버, 제목,장르번호, 내용, 작가


                    String title=txt_culture_review3_title.getText().toString();
                    String weather=text;//날씨
                    String place=text2+"/"+editText_select_trip2.getText().toString();//여행지
                    String content=txt_culture_review3_content.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,day,"3",title,weather,place,content,"save").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_reviewwrite3.this, "연결ok", Toast.LENGTH_SHORT).show();
                            Intent intent_btn_calsearchplus_save=new Intent(MainActivity_culture_reviewwrite3.this, MainActivity_culture_categorymain3.class);
                            startActivity(intent_btn_calsearchplus_save);
                            finish();
                        }
                    } catch (Exception e) {
                    }
                    ;
                    break;

            }
        }
    };
}
