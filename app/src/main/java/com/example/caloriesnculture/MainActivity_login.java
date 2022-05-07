package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class MainActivity_login extends AppCompatActivity {
    EditText userId, userPwd;
    Button loginBtn, joinBtn;
    SharedPreferences pref,pref2;
    SharedPreferences.Editor editor,editor2;
    TextView txt_id_find,txt_pw_find;
    CheckBox cb_login;
    String id, pw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref2 = getSharedPreferences("autoFile", MODE_PRIVATE);


        id = pref2.getString("id",null);
        pw = pref2.getString("pw",null);

        if(id!=null && pw!=null){
            Toast.makeText(MainActivity_login.this, id+"님 자동로그인입니다", Toast.LENGTH_SHORT).show();
            Intent intent_loginBtn2 = new Intent(MainActivity_login.this, MainActivity_main.class);
            startActivity(intent_loginBtn2);
            finish();
        }



        userId = (EditText) findViewById(R.id.userId);
        userPwd = (EditText) findViewById(R.id.userpwd);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(btnListener);
        joinBtn.setOnClickListener(btnListener);


        txt_id_find=(TextView)findViewById(R.id.txt_id_find);
        txt_id_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_loginBtn2 = new Intent(MainActivity_login.this, MainActivity_find_id.class);
                startActivity(intent_loginBtn2);
                finish();
            }
        });

        cb_login=(CheckBox)findViewById(R.id.cb_login);

    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/login.jsp");//바꿔주세요
                //http://192.168.56.1:8080/Cap_Connection_2/login_pra.jsp
                //http://106.241.33.158:1080/login.jsp
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (cb_login.isChecked()) {//자동로그인

                switch (view.getId()) {
                    case R.id.loginBtn: // 로그인 버튼 눌렀을 경우
                        String loginid = userId.getText().toString();
                        String loginpwd = userPwd.getText().toString();
                        try {

                            String result = new CustomTask().execute(loginid, loginpwd, "login").get();

                            if (result.equals("true")) {
                                Toast.makeText(MainActivity_login.this, "로그인", Toast.LENGTH_SHORT).show();
                                Intent intent_loginBtn = new Intent(MainActivity_login.this, MainActivity_main.class);
                                pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
                                editor = pref.edit();
                                editor.putString("nickname", loginid);
                                editor.commit();
                                pref2 = getSharedPreferences("autoFile", MODE_PRIVATE);
                                editor2 = pref2.edit();
                                editor2.putString("id", loginid);
                                editor2.putString("pw", loginpwd);
                                editor2.commit();
                                //intent_loginBtn.putExtra("nickname",loginid);
                                startActivity(intent_loginBtn);
                                finish();
                            } else if (result.equals("false")) {
                                Toast.makeText(MainActivity_login.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                                userId.setText("");
                                userPwd.setText("");
                            } else if (result.equals("noId")) {
                                Toast.makeText(MainActivity_login.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                                userId.setText("");
                                userPwd.setText("");
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case R.id.joinBtn: // 회원가입
                        Intent intent_joinBtn = new Intent(MainActivity_login.this, MainActivity_join.class);
                        startActivity(intent_joinBtn);
                        finish();
                        break;

                }
            }else{
                switch (view.getId()) {
                    case R.id.loginBtn: // 로그인 버튼 눌렀을 경우
                        String loginid = userId.getText().toString();
                        String loginpwd = userPwd.getText().toString();
                        try {

                            String result = new CustomTask().execute(loginid, loginpwd, "login").get();

                            if (result.equals("true")) {
                                Toast.makeText(MainActivity_login.this, "로그인", Toast.LENGTH_SHORT).show();
                                Intent intent_loginBtn = new Intent(MainActivity_login.this, MainActivity_main.class);
                                pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
                                editor = pref.edit();
                                editor.putString("nickname", loginid);
                                editor.commit();

                                //intent_loginBtn.putExtra("nickname",loginid);
                                startActivity(intent_loginBtn);
                                finish();
                            } else if (result.equals("false")) {
                                Toast.makeText(MainActivity_login.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                                userId.setText("");
                                userPwd.setText("");
                            } else if (result.equals("noId")) {
                                Toast.makeText(MainActivity_login.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                                userId.setText("");
                                userPwd.setText("");
                            }
                        } catch (Exception e) {
                        }
                        break;
                    case R.id.joinBtn: // 회원가입
                        Intent intent_joinBtn = new Intent(MainActivity_login.this, MainActivity_join.class);
                        startActivity(intent_joinBtn);
                        finish();
                        break;

                }
            }
        }
    };
}
//여기까지 OK 04/13
//더 추가한다면 비밀번호 정규식을 추가해볼것.