package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity_cal_day_result extends AppCompatActivity {

    SharedPreferences pref;
    String day="";
    String nickdata="";
    List<String> result_list;

    TextView bmikcal, mrnkcal, lunkcal, dinkcal, totkcal,plusminus,resulttot;
    TextView bike, run, walk;
    Button btn_calday_result_back;

    String bmikc="", mrnkc="", lunkc="", dinkc="", totkc="", plma="",resultkc="";
    String bikekc="", runkc="", walkkc="";
    @Override
    public void onBackPressed() {
        Intent intent_btn_calday_result_back=new Intent(MainActivity_cal_day_result.this, MainActivity_cal_day.class);
        startActivity(intent_btn_calday_result_back);
        finish();
        super.onBackPressed();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_day_result);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_cal","error");

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");


       /* btn_calday_result_back=findViewById(R.id.btn_calday_result_back);
        btn_calday_result_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calday_result_back=new Intent(MainActivity_cal_day_result.this, MainActivity_cal_day.class);
                startActivity(intent_btn_calday_result_back);
            }
        });*/

        bmikcal=(TextView)findViewById(R.id.txt_calday_result_userkcal);
        mrnkcal=(TextView)findViewById(R.id.txt_calday_result_mkcal);
        lunkcal=(TextView)findViewById(R.id.txt_calday_result_lkcal);
        dinkcal=(TextView)findViewById(R.id.txt_calday_result_dkcal);
        totkcal=(TextView)findViewById(R.id.txt_calday_result_totkcal_tot);
        plusminus=(TextView)findViewById(R.id.txt_calday_result_totkcal_1);
        resulttot=(TextView)findViewById(R.id.txt_calday_result_totkcal);
        bike=(TextView)findViewById(R.id.txt_calday_result_sport1);
        run=(TextView)findViewById(R.id.txt_calday_result_sport2);
        walk=(TextView)findViewById(R.id.txt_calday_result_sport3);


        result_list = new ArrayList<String>();

        try {
            String result = new CustomTask().execute("result_kcal",nickdata,day).get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_cal_day_result.this, result + "연결ok", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity_cal_day_result.this, result + "연결ok", Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();
                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }

                String stst = Integer.toString(n);
                for (int i = 0; i < n; i++) {
                    result_list.add(i, array[i]);
                }
                bmikc = result_list.get(0);
                mrnkc= result_list.get(1);
                lunkc= result_list.get(2);
                dinkc=result_list.get(3);
                totkc=result_list.get(4);
                plma=result_list.get(5);
                resultkc=result_list.get(6);
                bikekc=result_list.get(7);
                runkc=result_list.get(8);
                walkkc=result_list.get(9);
            }
        } catch (Exception e) {
        }

        bmikcal.setText(bmikc);mrnkcal.setText(mrnkc);lunkcal.setText(lunkc);dinkcal.setText(dinkc);
        plusminus.setText(plma);resulttot.setText(resultkc);
        totkcal.setText(totkc);bike.setText(bikekc);run.setText(runkc);walk.setText(walkkc);




    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/Result_kcal.jsp");//바꿔주세요
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
