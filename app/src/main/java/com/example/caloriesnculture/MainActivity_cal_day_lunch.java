package com.example.caloriesnculture;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class MainActivity_cal_day_lunch extends AppCompatActivity {
    String day = "";
    String nickdata = "";
    TextView nickname;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String name=""; String kcal="";

    CustomAdapter adapter;
    ListView calday_listView_lunch;

    @Override
    public void onBackPressed() {
        Intent intent_btn_caldayl_back=new Intent(MainActivity_cal_day_lunch.this, MainActivity_cal_day.class);
        startActivity(intent_btn_caldayl_back);
        finish();
        super.onBackPressed();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_day_lunch);

        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        day = pref.getString("inputdate_cal", "error");

        nickname=(TextView)findViewById(R.id.txt_caldayl_name);
        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");
        nickname.setText(nickdata);

        /*Button btn_caldayl_back=findViewById(R.id.btn_caldayl_back);
        btn_caldayl_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_caldayl_back=new Intent(MainActivity_cal_day_lunch.this, MainActivity_cal_day.class);
                startActivity(intent_btn_caldayl_back);
            }
        });*/

        Button btn_caldayl_search=findViewById(R.id.btn_caldayl_search);
        btn_caldayl_search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_caldayl_search=new Intent(MainActivity_cal_day_lunch.this, MainActivity_cal_searchl.class);
                startActivity(intent_btn_caldayl_search);
                finish();
            }
        });


        calday_listView_lunch = (ListView) findViewById(R.id.calday_listView_lunch);
        adapter=new CustomAdapter();
        calday_listView_lunch.setAdapter(adapter);

        calday_listView_lunch.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //????????? ???????????? ???????????? ?????????
                name=((FoodDTO)adapter.getItem(position)).getTitle();
                kcal=((FoodDTO)adapter.getItem(position)).getContent();

                Toast.makeText(MainActivity_cal_day_lunch.this, kcal+name, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity_cal_day_lunch.this);
                alert_confirm.setMessage("??????????????? ??????????????????????").setCancelable(false).setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent_selected_item = new Intent(MainActivity_cal_day_lunch.this, MainAcivity_cal_aditl.class);
                                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                                editor=pref.edit();
                                editor.putString("foodname_l_edit",name);
                                editor.commit();
                                startActivity(intent_selected_item);
                                finish();

                            }
                        }).setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in=new Intent(getApplicationContext(), MainActivity_cal_day_lunch.class);
                                startActivity(in);
                                // 'No'
                                //Toast.makeText(MainActivity_cal_day_morning.this, "??????", Toast.LENGTH_SHORT).show();

                                try {
                                    String result = new CustomTask().execute("lunch_del", nickdata, day,name).get();

                                    if (result.equals("ok")) {
                                        Toast.makeText(MainActivity_cal_day_lunch.this, "????????????", Toast.LENGTH_SHORT).show();
                                        //((MainActivity_cal_day_morning)(MainActivity_cal_day_morning.mContext)).onResume();
                                        try {
                                            String result2 = new CustomTask().execute("lunch", nickdata, day,"0").get();
                                            adapter.clear();
                                            if (result.equals("ok")) {
                                                Toast.makeText(MainActivity_cal_day_lunch.this, result2 + "??????ok", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                /*Toast.makeText(MainActivity_cal_day_lunch.this, result2 + "??????ok", Toast.LENGTH_SHORT).show();

                                                StringTokenizer st = new StringTokenizer(result, "&");
                                                int n = st.countTokens();
                                                String[] array = new String[st.countTokens()];

                                                int j = 0;
                                                while (st.hasMoreElements()) {
                                                    array[j++] = st.nextToken();
                                                }
                                                String a=array[0];
                                                String b=array[1];

                                                StringTokenizer st1 = new StringTokenizer(a, "+");
                                                int n1 = st1.countTokens();
                                                String[] array1 = new String[st1.countTokens()];
                                                int k = 0;
                                                while (st1.hasMoreElements()) {
                                                    array1[k++] = st1.nextToken();
                                                }

                                                StringTokenizer st2 = new StringTokenizer(b, "+");
                                                int n2 = st2.countTokens();
                                                String[] array2 = new String[st2.countTokens()];
                                                int l = 0;
                                                while (st2.hasMoreElements()) {
                                                    array2[l++] = st2.nextToken();
                                                }

                                                for (int i = 0; i < n1; i++) {
                                                    //list_calday_listView_morning.add(i, array[i]);
                                                    FoodDTO dto = new FoodDTO(array1[i],array2[i]);
                                                    adapter.addItem(dto);
                                                }*/

                                                adapter.notifyDataSetChanged();

                                            }
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        Toast.makeText(MainActivity_cal_day_lunch.this, "error", Toast.LENGTH_SHORT).show();


                                    }
                                } catch (Exception e) {
                                }
                                return;
                            }
                        });

                AlertDialog alert = alert_confirm.create();
                alert.show();

            }
        });


        try {
            String result = new CustomTask().execute("lunch", nickdata, day,"0").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_cal_day_lunch.this, result + "??????ok", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity_cal_day_lunch.this, result + "??????ok", Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();
                String[] array = new String[st.countTokens()];

                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }
                String a=array[0];
                String b=array[1];

                StringTokenizer st1 = new StringTokenizer(a, "+");
                int n1 = st1.countTokens();
                String[] array1 = new String[st1.countTokens()];
                int k = 0;
                while (st1.hasMoreElements()) {
                    array1[k++] = st1.nextToken();
                }

                StringTokenizer st2 = new StringTokenizer(b, "+");
                int n2 = st2.countTokens();
                String[] array2 = new String[st2.countTokens()];
                int l = 0;
                while (st2.hasMoreElements()) {
                    array2[l++] = st2.nextToken();
                }

                for (int i = 0; i < n1; i++) {

                    FoodDTO dto = new FoodDTO(array1[i],array2[i]);
                    adapter.addItem(dto);
                }

                adapter.notifyDataSetChanged();

            }
        } catch (Exception e) {
        }
    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/SelectLunch.jsp");//???????????????
                // "http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //http://106.241.33.158:1080/SelectMorning.jsp
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "type=" + strings[0] + "&nickname=" + strings[1] + "&date=" + strings[2] + "&foodnumber=" + strings[3];
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
                    Log.i("?????? ??????", conn.getResponseCode() + "??????");
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
