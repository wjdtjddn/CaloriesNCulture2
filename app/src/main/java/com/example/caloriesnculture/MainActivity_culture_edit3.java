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

public class MainActivity_culture_edit3 extends AppCompatActivity {

    EditText txt_culture_review3_title2, editText_select_trip,txt_culture_review3_content2;
    TextView txt_culture_review3_date2;

    ArrayAdapter<CharSequence> adspin1;
    Spinner sp; String text="";//년도변수

    ArrayAdapter<CharSequence> adspin2;
    Spinner sp2; String text2="";//년도변수

    Button btn_culture_review3_save2;
    SharedPreferences pref;
    String title="";String nickdata = "";
    String day="";
    String place1="";String weath="";
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_culture_edit3.this, MainActivity_culture_categorymain3.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_edit3);

        txt_culture_review3_title2=(EditText)findViewById(R.id.txt_culture_review3_title2);
        editText_select_trip=(EditText) findViewById(R.id.editText_select_trip);
        txt_culture_review3_content2=(EditText)findViewById(R.id.txt_culture_review3_content2);
        txt_culture_review3_date2=(TextView)findViewById(R.id.txt_culture_review3_date2);


        btn_culture_review3_save2 = findViewById(R.id.btn_culture_review3_save2);
        btn_culture_review3_save2.setOnClickListener(btnListener1);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        title=pref.getString("category3_title","error");
        txt_culture_review3_title2.setText(title);
        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");

        //여기서 저장한 데이터 정보를 받아오고
        try {
            String result = new CustomTask().execute(nickdata,"0","3",title,"0","0","0","culture3_info").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_culture_edit3.this, "연결ok", Toast.LENGTH_SHORT).show();
            }else{
                //받아온 값을 스트링토크나이저로 분리하는 코드 작성하고
                Toast.makeText(MainActivity_culture_edit3.this, result, Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();

                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }

                String s=array[2]; int k=0;
                StringTokenizer st1 = new StringTokenizer(s, "/");
                int n1=st1.countTokens();
                String[] array2 = new String[st1.countTokens()];
                while(st1.hasMoreElements()){
                    array2[k++]=st1.nextToken();
                }


                //받는순서 잘 생각하기(날짜+날씨+지역1+지역2+내용)//지역1+/파싱+지역2//
                String date=array[0];txt_culture_review3_date2.setText(date);
                weath=array[1];
                place1=array2[0];//cultureAuth2.setText(auth+genre);
                String place2=array2[1];editText_select_trip.setText(place2);

                String cont=array[3];txt_culture_review3_content2.setText(cont);


            }
        } catch (Exception e) {
        }

        //*스피너의 경우에는 어떤것을 선택한것인지 받아서 설정해주기.
        sp2 = (Spinner)findViewById(R.id.spinner_select_dis3_trip);
        adspin2 = ArrayAdapter.createFromResource(this, R.array.county1, android.R.layout.simple_spinner_dropdown_item);

        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adspin2);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text2=adspin2.getItem(position).toString();
                Toast.makeText(MainActivity_culture_edit3.this, text2, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        sp = (Spinner)findViewById(R.id.spinner_culture_review3_author2);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text=adspin1.getItem(position).toString();
                Toast.makeText(MainActivity_culture_edit3.this, text, Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int num1=adspin2.getPosition(place1);
        sp2.setSelection(num1);
        int num2=adspin1.getPosition(weath);
        sp.setSelection(num2);

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

                case R.id.btn_culture_review3_save2:
                    //컬쳐번호,프라이머리넘버, 날짜, 카테고리넘버, 제목,장르번호, 내용, 작가

                    String title=txt_culture_review3_title2.getText().toString();
                    String weather=text;
                    String place=text2+"/"+editText_select_trip.getText().toString();
                    String content=txt_culture_review3_content2.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,day,"3",title,weather,place,content,"culture3_edit").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_edit3.this, "연결ok", Toast.LENGTH_SHORT).show();
                            Intent intent_btn_calsearchplus_save=new Intent(MainActivity_culture_edit3.this, MainActivity_culture_categorymain3.class);
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