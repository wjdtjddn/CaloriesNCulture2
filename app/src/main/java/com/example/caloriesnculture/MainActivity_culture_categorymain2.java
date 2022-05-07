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

public class MainActivity_culture_categorymain2 extends AppCompatActivity {

    String nickdata = "";
    SharedPreferences pref;


    SharedPreferences.Editor editor;

    culture34_CustomAdapter adapter;String click_title=""; String content="";
    ListView listview_categorymain2_1;
    //List<String> list_calday_listView_categorymain2_1;
    //ArrayAdapter<String> adapter_calday_listView_categorymain2_1;
    Button btn_culture_categorymain2_search;
    EditText txt_culture_categorymain2_search;

    @Override
    public void onBackPressed() {
        Intent intent_btn_culture_categorymain2_back = new Intent(MainActivity_culture_categorymain2.this, MainActivity_culture_main.class);
        startActivity(intent_btn_culture_categorymain2_back);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_categorymain2);

        pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
        nickdata = pref.getString("nickname", "error");

        txt_culture_categorymain2_search=(EditText)findViewById(R.id.txt_culture_categorymain2_search);
        btn_culture_categorymain2_search=(Button)findViewById(R.id.btn_culture_categorymain2_search);
        btn_culture_categorymain2_search.setOnClickListener(btnListener1);

        listview_categorymain2_1 = (ListView) findViewById(R.id.listview_categorymain2_1);
        //list_calday_listView_categorymain2_1 = new ArrayList<String>();
        //adapter_calday_listView_categorymain2_1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_calday_listView_categorymain2_1);
        //listview_categorymain2_1.setAdapter(adapter_calday_listView_categorymain2_1);
        adapter=new culture34_CustomAdapter();
        listview_categorymain2_1.setAdapter(adapter);

        listview_categorymain2_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                //click_title = (String) adapterView.getItemAtPosition(position);

                click_title=((CultureDTO)adapter.getItem(position)).getTitle();
                content=((CultureDTO)adapter.getItem(position)).getContent();

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity_culture_categorymain2.this);
                alert_confirm.setMessage("어떤작업을 수행하실건가요?").setCancelable(false).setPositiveButton("수정",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent_selected_item = new Intent(MainActivity_culture_categorymain2.this, MainActivity_culture_edit2.class);
                                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                                editor=pref.edit();
                                editor.putString("category2_title",click_title);
                                editor.commit();
                                startActivity(intent_selected_item);
                                finish();

                            }
                        }).setNegativeButton("삭제",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in=new Intent(getApplicationContext(), MainActivity_culture_categorymain2.class);
                                startActivity(in);
                                try {
                                    String result = new CustomTask().execute(nickdata, "0", "2", click_title, "0", "0", "0", "culture2_delete").get();

                                    if (result.equals("ok")) {
                                        Toast.makeText(MainActivity_culture_categorymain2.this, "삭제완료", Toast.LENGTH_SHORT).show();
                                        //((MainActivity_cal_day_morning)(MainActivity_cal_day_morning.mContext)).onResume();
                                        try {
                                            String result2 = new CustomTask().execute(nickdata, "0", "2", "0", "0", "0", "0", "culture2_select").get();
                                            adapter.clear();
                                            if (result.equals("ok")) {
                                                Toast.makeText(MainActivity_culture_categorymain2.this, "연결ok", Toast.LENGTH_SHORT).show();
                                                //list_calday_listView_categorymain2_1.add(0, "데이터가 없습니다");
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                adapter.clear();
                                                //MainActivity_culture_categorymain2.invalidate();
                                                Toast.makeText(MainActivity_culture_categorymain2.this, result2 + "연결ok", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(MainActivity_culture_categorymain2.this, "error", Toast.LENGTH_SHORT).show();


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





        Button btn_culture_categorymain2_plus=findViewById(R.id.btn_culture_categorymain2_plus);

        btn_culture_categorymain2_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain2_plus = new Intent(MainActivity_culture_categorymain2.this, MainActivity_culture_date2.class);
                startActivity(intent_btn_culture_categorymain2_plus);
                finish();
            }
        });


        try {
            String result = new CustomTask().execute(nickdata, "0", "2", "0", "0", "0", "0", "culture2_select").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_culture_categorymain2.this, result + "연결ok", Toast.LENGTH_SHORT).show();
                //list_calday_listView_categorymain2_1.add(0, "데이터가 없습니다");
                adapter.notifyDataSetChanged();
            } else {
                adapter.clear();
                Toast.makeText(MainActivity_culture_categorymain2.this, result + "연결ok", Toast.LENGTH_SHORT).show();
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
                URL url = new URL("http://106.241.33.158:1080/culture2_write.jsp");
                //바꿔주세요//"http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
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
    View.OnClickListener btnListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view1) {

            switch (view1.getId()) {

                case R.id.btn_culture_categorymain2_search:
                    //컬쳐번호,프라이머리넘버, 날짜, 카테고리넘버, 제목,장르번호, 내용, 작가

                    String search=txt_culture_categorymain2_search.getText().toString();

                    try {
                        String result = new CustomTask().execute(nickdata,"0","2",search,"0","0","0","culture2_find").get();

                        if (result.equals("ok")) {
                            Toast.makeText(MainActivity_culture_categorymain2.this, "연결ok", Toast.LENGTH_SHORT).show();
                            txt_culture_categorymain2_search.setText("");
                            //list_calday_listView_categorymain2_1.add(0,result);
                            adapter.notifyDataSetChanged();

                        } else  {
                            adapter.clear();
                            Toast.makeText(MainActivity_culture_categorymain2.this, result+"연결ok", Toast.LENGTH_SHORT).show();
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
                            txt_culture_categorymain2_search.setText("");


                        }
                    } catch (Exception e) {
                    }
                    break;

            }
        }
    };
}
