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


public class MainActivity_culture_reviewwrite2 extends AppCompatActivity {

    //화면상의 위젯 선언
    EditText cultureTitle,cultureAuth,reviewContent;//제목, 저자, 내용을 입력받는 에디트텍스트 선언
    Button btn_culture_review2_save;//저장하는 버튼 선언
    TextView txt_culture_review2_date;//날짜텍스트뷰선언
    ArrayAdapter<CharSequence> adspin1; Spinner sp;//스피너 즉, 장르 선택하는 스피너+어댑터 선언
    String text="";//스피너로 선택한 값을 받아올 스트링변수

    //코드상 필요한 데이터를 받아올 변수 선언
    SharedPreferences pref;//파일로 저장한 닉네임과 날짜를 읽을 셰어드프리퍼런스변수 선언.
    String day=""; String nickdata = "";//날짜와 닉네임을 저장할 스트링변수선언.
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_culture_reviewwrite2.this, MainActivity_culture_categorymain2.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_reviewwrite2);

        //선언한 위젯변수와 화면상의 위젯 연결
        cultureTitle=(EditText)findViewById(R.id.cultureTitle);
        cultureAuth=(EditText)findViewById(R.id.cultureAuth);
        reviewContent=(EditText)findViewById(R.id.reviewContent);
        txt_culture_review2_date=(TextView)findViewById(R.id.txt_culture_review2_date);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_culture","error");
        txt_culture_review2_date.setText(day);

        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");

        //장르선택하는 스피너 위젯과 연결하고 어댑터 달고,
        sp = (Spinner)findViewById(R.id.spinner_culture2_genre);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.genre2, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);

        //선택한 아이템 값 받아오는 과정
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text=adspin1.getItem(position).toString();
                Toast.makeText(MainActivity_culture_reviewwrite2.this, text, Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //저장버튼+리스너 생성
        btn_culture_review2_save = findViewById(R.id.btn_culture_review2_save);
        btn_culture_review2_save.setOnClickListener(btnListener1);

    }

    //jsp와 통신하는부분
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

                case R.id.btn_culture_review2_save:
                    //컬쳐번호,프라이머리넘버, 날짜, 카테고리넘버, 제목,장르번호, 내용, 작가

                    String title=cultureTitle.getText().toString();
                    String author=cultureAuth.getText().toString();//날씨
                    String genre=text;
                    String content=reviewContent.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,day,"2",title,author,genre,content,"save").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_reviewwrite2.this, "연결ok", Toast.LENGTH_SHORT).show();
                            Intent intent_btn_calsearchplus_save=new Intent(MainActivity_culture_reviewwrite2.this, MainActivity_culture_categorymain2.class);
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






/*
 *  @Override
 *     protected void onActivityResult (int requestCode, int resultCode, Intent intent){
 *
 *         super.onActivityResult(requestCode, resultCode, intent);
 *         Toast.makeText(MainActivity_culture_reviewwrite2.this, "돌아옴", Toast.LENGTH_SHORT).show();
 *         if(resultCode == 0 ){
 *             TextView genreName = (TextView) findViewById(R.id.genreName);
 *
 *             if(intent.getStringExtra("genreName") != null){
 *                 genreName.setText(intent.getStringExtra("genreName"));
 *             }else{
 *                 genreName.setText("소설");
 *             }
 *         }
 *     };
 *
 *
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_culture_reviewwrite2);
 *
 *         Button btn_culture_review2_save = findViewById(R.id.btn_culture_review2_save);
 *         btn_culture_review2_save.setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View v) {
 *                   Intent intent_btn_culture_review2_save = new Intent(MainActivity_culture_reviewwrite2.this, MainActivity_culture_categorymain2.class);
 *                  startActivity(intent_btn_culture_review2_save);//액티비티 이동
         *try{
         *String result=new CustomTask().execute("join").get();
         *
         *if(result!=null&&"".equals(result)){
         *if("OK".equals(result)){
         *Toast.makeText(MainActivity_culture_reviewwrite2.this,"리뷰를 저장하였습니다.",Toast.LENGTH_SHORT).show();
         *}
         *}else{
         *throw new Exception("리뷰 저장을 실패했습니다.");
         *}
         *
         *}catch(ExecutionException e){
         *e.printStackTrace();
         *}catch(InterruptedException e){
         *e.printStackTrace();
         *}catch(Exception e){
         *Toast.makeText(MainActivity_culture_reviewwrite2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
         *}
         *}
         *});
         *}
         *
         *
         *

class CustomTask extends AsyncTask<String, Void, String> {
 *
    String sendMsg1, receiveMsg1;
 *
    ResultSet rs;
 *
    String sendMsg2;
 *
    @Override
 *

    protected String doInBackground(String... strings) {
 *try {
 * /*
             *                 cultureTitle
             *                         cultureAuth
             *                 genreName
             *                         reviewContent
 *String str;
 *String insertParam;
 *
            URL url = new URL("http://192.168.35.196:8090/review/insertReview.jsp");//바꿔주세요//http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
 *                 // 192.168.56.1
 *HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 *conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 *conn.setRequestMethod("POST");
 *OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
 *                 // sendMsg1 = "id="+strings[0]+"&pwd="+strings[1]+"&gender="+strings[2]+"&birth="+strings[3]+"&height="+strings[4]+"&weight="+strings[5]+"&type="+strings[6];
 *
            insertParam = "cultureTitle=제목01&cultureAuth=저자_유지현&genreName=소설&reviewContent=우와 대박 진짜 너무너무너무 재미써여!";
 *osw.write(insertParam);
 *osw.flush();
 *
 *if (conn.getResponseCode() == conn.HTTP_OK) {
 *InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
 *BufferedReader reader = new BufferedReader(tmp);
 *StringBuffer buffer = new StringBuffer();
 *while ((str = reader.readLine()) != null) {
 *buffer.append(str);
 *}
 *receiveMsg1 = buffer.toString();
 *
 *
 *} else {
 *Log.i("통신 결과", conn.getResponseCode() + "에러");
 *}
 *
 *
 *} catch (MalformedURLException e) {
 *e.printStackTrace();
 *} catch (IOException e) {
 *e.printStackTrace();
 *}
 *return receiveMsg1;
 *}
 *
}
 **/