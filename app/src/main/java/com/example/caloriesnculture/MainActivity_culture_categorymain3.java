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
import android.widget.EditText;
import android.widget.ListView;
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

public class MainActivity_culture_categorymain3 extends AppCompatActivity {

    String nickdata = "";
    SharedPreferences pref;


    SharedPreferences.Editor editor;
    EditText txt_culture_categorymain3_search;
    Button btn_culture_categorymain3_search;

    culture34_CustomAdapter adapter;
    ListView listview_categorymain3_1;
    String click_title=""; String content="";
    //List<String> list_calday_listView_categorymain3_1;
    //ArrayAdapter<String> adapter_calday_listView_categorymain3_1;
    @Override
    public void onBackPressed() {
        Intent intent_btn_culture_categorymain3_back = new Intent(MainActivity_culture_categorymain3.this, MainActivity_culture_main.class);
        startActivity(intent_btn_culture_categorymain3_back);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_categorymain3);

        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");

        txt_culture_categorymain3_search=(EditText)findViewById(R.id.txt_culture_categorymain3_search);
        btn_culture_categorymain3_search=(Button)findViewById(R.id.btn_culture_categorymain3_search);
        btn_culture_categorymain3_search.setOnClickListener(btnListener1);


        listview_categorymain3_1 = (ListView) findViewById(R.id.listview_categorymain3_1);
       // list_calday_listView_categorymain3_1 = new ArrayList<String>();
       // adapter_calday_listView_categorymain3_1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_calday_listView_categorymain3_1);
       // listview_categorymain3_1.setAdapter(adapter_calday_listView_categorymain3_1);
        adapter=new culture34_CustomAdapter();
        listview_categorymain3_1.setAdapter(adapter);

        listview_categorymain3_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(final AdapterView<?> adapterView,
                                    View view, int position, long id) {

                click_title=((CultureDTO)adapter.getItem(position)).getTitle();
                content=((CultureDTO)adapter.getItem(position)).getContent();
                //????????? ???????????? ???????????? ?????????
                //click_title = (String) adapterView.getItemAtPosition(position);
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity_culture_categorymain3.this);
                alert_confirm.setMessage("??????????????? ??????????????????????").setCancelable(false).setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent_selected_item = new Intent(MainActivity_culture_categorymain3.this, MainActivity_culture_edit3.class);
                                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                                editor=pref.edit();
                                editor.putString("category3_title",click_title);
                                editor.commit();
                                startActivity(intent_selected_item);
                                finish();

                            }
                        }).setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in=new Intent(getApplicationContext(), MainActivity_culture_categorymain3.class);
                                startActivity(in);
                                try {
                                    String result = new CustomTask().execute(nickdata, "0", "3", click_title, "0", "0", "0", "culture3_delete").get();

                                    if (result.equals("ok")) {
                                        Toast.makeText(MainActivity_culture_categorymain3.this, "????????????", Toast.LENGTH_SHORT).show();
                                        //((MainActivity_cal_day_morning)(MainActivity_cal_day_morning.mContext)).onResume();
                                        try {
                                            String result2 = new CustomTask().execute(nickdata, "0", "3", "0", "0", "0", "0", "culture3_select").get();
                                            adapter.clear();
                                            if (result.equals("ok")) {
                                                Toast.makeText(MainActivity_culture_categorymain3.this, result2 + "??????ok", Toast.LENGTH_SHORT).show();
                                               // list_calday_listView_categorymain3_1.add(0, "???????????? ????????????");
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                adapter.clear();
                                                Toast.makeText(MainActivity_culture_categorymain3.this, result2 + "??????ok", Toast.LENGTH_SHORT).show();
                                                //title+content???????????? ?????? ?????????
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
                                                    CultureDTO dto = new CultureDTO(array1[i],array2[i]);
                                                    adapter.addItem(dto);
                                                }


                                                adapter.notifyDataSetChanged();


                                            }
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        Toast.makeText(MainActivity_culture_categorymain3.this, "error", Toast.LENGTH_SHORT).show();


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
        Button btn_culture_categorymain3_plus = findViewById(R.id.btn_culture_categorymain3_plus);

        btn_culture_categorymain3_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain3_plus = new Intent(MainActivity_culture_categorymain3.this, MainActivity_culture_date3.class);
                startActivity(intent_btn_culture_categorymain3_plus);
                finish();
            }
        });

       /* Button btn_culture_categorymain3_back = findViewById(R.id.btn_culture_categorymain3_back);

        btn_culture_categorymain3_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain3_back = new Intent(MainActivity_culture_categorymain3.this, MainActivity_culture_main.class);
                startActivity(intent_btn_culture_categorymain3_back);
                finish();
            }
        });*/


/*sendMsg1 = "nickname="+strings[0]+"&day="+strings[1]+"&category="+strings[2]+"&title="+strings[3]+
"&weather="+strings[4]+"&place="+strings[5]+"&content="+strings[6]+"&type="+strings[7];
 */


        try {
            String result = new CustomTask().execute(nickdata, "0", "3", "0", "0", "0", "0", "culture3_select").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_culture_categorymain3.this, result + "??????ok", Toast.LENGTH_SHORT).show();
                //list_calday_listView_categorymain3_1.add(0, "???????????? ????????????");
                adapter.notifyDataSetChanged();
            } else {
                adapter.clear();
                Toast.makeText(MainActivity_culture_categorymain3.this, result + "??????ok", Toast.LENGTH_SHORT).show();

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
                        CultureDTO dto = new CultureDTO(array1[i],array2[i]);
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
                URL url = new URL("http://106.241.33.158:1080/culture3_write.jsp");
                //???????????????//"http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //http://106.241.33.158:1080/SelectMorning.jsp
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "nickname="+strings[0]+"&day="+strings[1]+"&category="+strings[2]+"&title="+strings[3]+"&weather="+strings[4]+"&place="+strings[5]+"&content="+strings[6]+"&type="+strings[7];
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
    View.OnClickListener btnListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view1) {

            switch (view1.getId()) {

                case R.id.btn_culture_categorymain3_search:
                    //????????????,?????????????????????, ??????, ??????????????????, ??????,????????????, ??????, ??????

                    String search=txt_culture_categorymain3_search.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,"0","3",search,"0","0","0","culture3_find").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_categorymain3.this, "??????ok", Toast.LENGTH_SHORT).show();
                            txt_culture_categorymain3_search.setText("");
                            //list_calday_listView_categorymain3_1.add(0,result);
                            adapter.notifyDataSetChanged();

                        } else  {
                            adapter.clear();
                            Toast.makeText(MainActivity_culture_categorymain3.this, result+"??????ok", Toast.LENGTH_SHORT).show();
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
                                CultureDTO dto = new CultureDTO(array1[i],array2[i]);
                                adapter.addItem(dto);
                            }


                            adapter.notifyDataSetChanged();
                            txt_culture_categorymain3_search.setText("");

                        }
                    } catch (Exception e) {
                    }
                    break;

            }
        }
    };
}
