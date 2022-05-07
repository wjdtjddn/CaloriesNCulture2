package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity_cal_day extends AppCompatActivity {
    TextView date,nickname;
    String day="";
    String nickdata="";
    SharedPreferences pref;
    List<String> kcal_list;
    String mor="";String lun= ""; String din="";String tot="";
    TextView txtMorning, txtLunch, txtDinner,txtTot;
    Button btn_calday_morning,btn_calday_lunch,btn_calday_dinner;
    @Override
    public void onBackPressed() {
        Intent intent_btn_calday_menu=new Intent(MainActivity_cal_day.this, MainActivity_cal_main.class);
        startActivity(intent_btn_calday_menu);
        finish();
        super.onBackPressed();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_day);
        date=(TextView)findViewById(R.id.txt_calday_date);
        //Intent intent_calday_date=getIntent();
        //day=intent_calday_date.getStringExtra("today");
        //nick=intent_calday_date.getStringExtra("nick");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_cal","error");
        date.setText(day);

        nickname=(TextView)findViewById(R.id.txt_calday_name);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        nickname.setText(nickdata);

        /*Button btn_calday_menu=findViewById(R.id.btn_calday_menu);
        btn_calday_menu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_menu=new Intent(MainActivity_cal_day.this, MainActivity_cal_main.class);
                startActivity(intent_btn_calday_menu);
            }
        });*/
        btn_calday_morning=findViewById(R.id.btn_calday_morning);
        btn_calday_morning.setBackgroundResource(R.drawable.button_back);
        btn_calday_morning.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_morning=new Intent(MainActivity_cal_day.this, MainActivity_cal_day_morning.class);
               // intent_btn_calday_morning.putExtra("today",day);
               // intent_btn_calday_morning.putExtra("nick2",nick);
                startActivity(intent_btn_calday_morning);
                finish();
            }
        });
        btn_calday_lunch=findViewById(R.id.btn_calday_lunch);
        btn_calday_lunch.setBackgroundResource(R.drawable.button_back);
        btn_calday_lunch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_lunch=new Intent(MainActivity_cal_day.this, MainActivity_cal_day_lunch.class);
                startActivity(intent_btn_calday_lunch);
                finish();
            }
        });
        btn_calday_dinner=findViewById(R.id.btn_calday_dinner);
        btn_calday_lunch.setBackgroundResource(R.drawable.button_back);
        btn_calday_dinner.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_dinner=new Intent(MainActivity_cal_day.this, MainActivity_cal_day_dinner.class);
                startActivity(intent_btn_calday_dinner);
                finish();
            }
        });
        Button btn_calday_result=findViewById(R.id.btn_calday_result);
        btn_calday_result.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_result=new Intent(MainActivity_cal_day.this, MainActivity_cal_day_result.class);
                startActivity(intent_btn_calday_result);
                finish();
            }
        });
        txtMorning=(TextView)findViewById(R.id.textView);
        txtLunch=(TextView)findViewById(R.id.txt_calday_cal_lunch);
        txtDinner=(TextView)findViewById(R.id.txt_calday_cal_dinner);
        txtTot=(TextView)findViewById(R.id.txt_calday_cal_total);

        kcal_list = new ArrayList<String>();
        try {
            String result = new CustomTask().execute("cal_day",nickdata,day).get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_cal_day.this, result + "연결ok", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity_cal_day.this, result + "연결ok", Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();
                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }

                String stst = Integer.toString(n);
                for (int i = 0; i < n; i++) {
                    kcal_list.add(i, array[i]);
                }
                mor = kcal_list.get(0);
                lun= kcal_list.get(1);
                din= kcal_list.get(2);
                tot=kcal_list.get(3);
            }
        } catch (Exception e) {
        }
        txtMorning.setText(mor);txtLunch.setText(lun);txtDinner.setText(din);txtTot.setText(tot);



    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/Selectkcal.jsp");//바꿔주세요
                // "http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //http://106.241.33.158:1080/login.jsp
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "type=" + strings[0] + "&nickname=" + strings[1] + "&date=" + strings[2];
                osw.write(sendMsg1);
                osw.flush();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg1 = buffer.toString();


                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg1;
        }
    }
}
