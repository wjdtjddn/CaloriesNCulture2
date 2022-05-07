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
import java.util.StringTokenizer;

public class MainActivity_culture_edit2 extends AppCompatActivity {

    EditText cultureTitle2,cultureAuth2,reviewContent2;
    TextView txt_culture_review2_date2;
    Button btn_culture_review2_save2;
    String title="";
    SharedPreferences pref;
    String nickdata = "";
    String day=""; String auth="";String cont=""; String gen="";

    String genre="";
    ArrayAdapter<CharSequence> adspin1; Spinner sp;//스피너 즉, 장르 선택하는 스피너+어댑터 선언
    String text="";//스피너로 선택한 값을 받아올 스트링변수
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_culture_edit2.this, MainActivity_culture_categorymain2.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_edit2);

        cultureTitle2=(EditText)findViewById(R.id.cultureTitle2);
        cultureAuth2=(EditText)findViewById(R.id.cultureAuth2);
        reviewContent2=(EditText)findViewById(R.id.reviewContent2);
        txt_culture_review2_date2=(TextView)findViewById(R.id.txt_culture_review2_date2);






        btn_culture_review2_save2 = findViewById(R.id.btn_culture_review2_save2);
        btn_culture_review2_save2.setOnClickListener(btnListener1);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        title=pref.getString("category2_title","error");
        cultureTitle2.setText(title);
        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");


        try {
            String result = new CustomTask().execute(nickdata,"0","2",title,"0","0","0","culture2_info").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_culture_edit2.this, "연결ok", Toast.LENGTH_SHORT).show();
            }else{
                //받아온 값을 스트링토크나이저로 분리하는 코드 작성하고
                Toast.makeText(MainActivity_culture_edit2.this, result, Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();

                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }
                String a = "";
                String b = "";
                String stst = Integer.toString(n);

                String date=array[0];txt_culture_review2_date2.setText(date);
                String auth=array[1];cultureAuth2.setText(auth);
                genre= array[2];
                String con=array[3];reviewContent2.setText(con);
            }
        } catch (Exception e) {
        }
        //위젯에 값 넣어주는 코드넣어주기
        //장르선택하는 스피너 위젯과 연결하고 어댑터 달고,
        sp = (Spinner)findViewById(R.id.spinner_culture2_genre2);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.genre2, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);


        //선택한 아이템 값 받아오는 과정
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text=adspin1.getItem(position).toString();
                Toast.makeText(MainActivity_culture_edit2.this, text, Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int num1=adspin1.getPosition(genre);
        sp.setSelection(num1);


    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                //이부분은 본인 ip로 바꾸어 test해주세요
                URL url = new URL("http://106.241.33.158:1080/culture2_write.jsp");//바꿔주세요
                // "http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"//교수님서버
                //
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //nickdata,day,"3",title,author,genre,content,"save"
                sendMsg1 = "nickname="+strings[0]+"&day="+strings[1]+"&category="+strings[2]+"&title="+strings[3]+"&author="+strings[4]+"&genre="+strings[5]+"&content="+strings[6]+"&type="+strings[7];
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

                case R.id.btn_culture_review2_save2:
                    //컬쳐번호,프라이머리넘버, 날짜, 카테고리넘버, 제목,장르번호, 내용, 작가

                    String title=cultureTitle2.getText().toString();
                    String author=cultureAuth2.getText().toString();//날씨
                    String genre=text;
                    String content=reviewContent2.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,day,"2",title,author,genre,content,"culture2_edit").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_edit2.this, "연결ok", Toast.LENGTH_SHORT).show();
                            Intent intent_btn_calsearchplus_save=new Intent(MainActivity_culture_edit2.this, MainActivity_culture_categorymain2.class);
                            startActivity(intent_btn_calsearchplus_save);
                            finish();
                        }
                    } catch (Exception e) {
                    }
                    break;

            }
        }
    };
}
