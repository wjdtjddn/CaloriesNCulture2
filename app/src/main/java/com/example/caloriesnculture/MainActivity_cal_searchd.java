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
import android.widget.ListView;
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

public class MainActivity_cal_searchd extends AppCompatActivity {

    Button btn_calsearch_search3;
    EditText txt_calsearch_search3;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    ListView listView_searchd;
    List<String> listview_searchd_data;
    ArrayAdapter<String> adapter_listview_searchd;
    String selected_item3="";

    String day="";
    String nickdata="";

    @Override
    public void onBackPressed() {
        Intent intent_btn_calsearch_home3=new Intent(MainActivity_cal_searchd.this, MainActivity_cal_day_dinner.class);
        startActivity(intent_btn_calsearch_home3);
        finish();
        super.onBackPressed();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_searchd);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_cal","error");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");

        txt_calsearch_search3=(EditText)findViewById(R.id.txt_calsearch_search3);

        btn_calsearch_search3=(Button)findViewById(R.id.btn_calsearch_search3);
        btn_calsearch_search3.setOnClickListener(btnListener1);

        listView_searchd=(ListView)findViewById(R.id.calsearch_listView_food3);
        listview_searchd_data=new ArrayList<String>();
        adapter_listview_searchd=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listview_searchd_data);
        listView_searchd.setAdapter(adapter_listview_searchd);


        listView_searchd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                selected_item3 = (String)adapterView.getItemAtPosition(position);
                Intent intent_selected_item=new Intent(MainActivity_cal_searchd.this, MainActivity_cal_searchplusd.class);

                //intent_selected_item.putExtra("foodname",selected_item);
                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                editor=pref.edit();
                editor.putString("foodname_d",selected_item3);
                editor.commit();
                //intent_selected_item.putExtra("search_mdate",day);
                //intent_selected_item.putExtra("search_mdnick",nickdata);
                startActivity(intent_selected_item);
                finish();


            }
        });

        /*Button btn_calsearch_plus3=findViewById(R.id.btn_calsearch_plus3);
        btn_calsearch_plus3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calsearch_plus3=new Intent(MainActivity_cal_searchd.this, MainActivity_cal_searchplusd.class);
                startActivity(intent_btn_calsearch_plus3);
            }
        });
        Button btn_calsearch_home3=findViewById(R.id.btn_calsearch_home3);
        btn_calsearch_home3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calsearch_home3=new Intent(MainActivity_cal_searchd.this, MainActivity_cal_day_dinner.class);
                startActivity(intent_btn_calsearch_home3);
            }
        });*/

    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/SearchFoodD.jsp");//바꿔주세요//"http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "search_mnick="+strings[0]+"&search_mname="+strings[1]+"&search_mdate="+strings[2]+"&search_mgram="+strings[3]+"&type="+strings[4];
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

                case R.id.btn_calsearch_search3:

                    String search_d = txt_calsearch_search3.getText().toString();

                    if(search_d.equals("")||search_d==null){
                        listview_searchd_data.clear();
                        adapter_listview_searchd.notifyDataSetChanged();
                    }
                    else{
                        try {
                            String result = new CustomTask().execute("0",search_d,"0","0","search_d").get();

                            if (result.equals("ok")) {
                                Toast.makeText(MainActivity_cal_searchd.this, result+"연결ok", Toast.LENGTH_SHORT).show();
                                txt_calsearch_search3.setText("");
                                listview_searchd_data.add(0,result);
                                adapter_listview_searchd.notifyDataSetChanged();
                            }else  {
                                Toast.makeText(MainActivity_cal_searchd.this, result+"연결ok", Toast.LENGTH_SHORT).show();
                                StringTokenizer st=new StringTokenizer(result,"&");
                                int n=st.countTokens();
                                String [] array = new String[st.countTokens()];
                                int j=0;
                                while(st.hasMoreElements())
                                {
                                    array[j++]=st.nextToken();
                                }
                                String a="";
                                String b="";
                                String stst=Integer.toString(n);
                                for(int i=0; i<n; i++)
                                {
                                    listview_searchd_data.add(i,array[i]);
                                }
                                txt_calsearch_search3.setText("");
                                adapter_listview_searchd.notifyDataSetChanged();

                            }
                        } catch (Exception e) {
                        }
                    }

                    // Toast.makeText(MainActivity_cal_searchm.this, search_m, Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
}
