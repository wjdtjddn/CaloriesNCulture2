package com.example.caloriesnculture;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity_find_id extends AppCompatActivity {

    //아이디찾기
    EditText txt_find_phone,txt_find_phone2;
    EditText txt_find_name_id;
    Button btn_find_idid;
    ArrayAdapter<CharSequence> adspin1;
    Spinner sp; String text="";//년도변수


    //비밀번호찾기
    EditText txt_find_find_Id;
    Button btn_find_pwpw2;
    @Override
    public void onBackPressed() {
        Intent intent_btn_calsearch_home3=new Intent(MainActivity_find_id.this, MainActivity_login.class);
        startActivity(intent_btn_calsearch_home3);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        //Textview연결
        txt_find_phone=(EditText)findViewById(R.id.txt_find_phone);
        txt_find_phone2=(EditText)findViewById(R.id.txt_find_phone2);
        txt_find_name_id=(EditText)findViewById(R.id.txt_find_name_id);
        txt_find_find_Id=(EditText)findViewById(R.id.txt_find_find_Id);

        //button연결+리스너연결
        btn_find_idid=(Button)findViewById(R.id.btn_find_idid);
        btn_find_idid.setOnClickListener(btnListener1);
        btn_find_pwpw2=(Button)findViewById(R.id.btn_find_pwpw2);
        btn_find_pwpw2.setOnClickListener(btnListener1);

        sp = (Spinner)findViewById(R.id.spinner_Phone_find);
        adspin1 = ArrayAdapter.createFromResource(this, R.array.number_Phone, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text=adspin1.getItem(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/find_id.jsp");//바꿔주세요
                // http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // 192.168.56.1
                //"http://106.241.33.158:1080/join.jsp"
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "name="+strings[0]+"&phone="+strings[1]+"&id="+strings[2]+"&type="+strings[3];
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

                case R.id.btn_find_idid : //id찾기

                    String name= txt_find_name_id.getText().toString();//이름
                    String phone=text+"-"+txt_find_phone2.getText().toString()+"-"+txt_find_phone.getText().toString();


                    try {

                        String result  = new CustomTask().execute(name,phone,"0","id_find").get();

                        if(result.equals("ok")) {//존재하는 id가 없는경우
                            Toast.makeText(MainActivity_find_id.this,"존재하는 회원정보가 없습니다.",Toast.LENGTH_SHORT).show();
                            txt_find_name_id.setText("");
                            txt_find_phone.setText("");
                            txt_find_phone2.setText("");
                            int num1=adspin1.getPosition("010");
                            sp.setSelection(num1);
                        }

                        else{

                            // Toast.makeText(MainActivity_join.this,"pw는 1개이상의 ",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_find_id.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("id는"+result+"입니다");
                        alert.show();
                        txt_find_name_id.setText("");
                        txt_find_phone.setText("");
                        txt_find_phone2.setText("");
                        int num1=adspin1.getPosition("010");
                        sp.setSelection(num1);
                    }
                    }catch (Exception e) {}
                    break;

                case R.id.btn_find_pwpw2 : //pw찾기

                    String id= txt_find_find_Id.getText().toString();//이름

                    try {

                        String result  = new CustomTask().execute("0","0",id,"pw_find").get();

                        if(result.equals("ok")) {//존재하는 id가 없는경우
                            Toast.makeText(MainActivity_find_id.this,"존재하는 회원정보가 없습니다.",Toast.LENGTH_SHORT).show();
                            txt_find_find_Id.setText("");
                        }



                        else{

                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_find_id.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("pw는"+result+"입니다");
                            alert.show();
                            txt_find_find_Id.setText("");

                        }
                    }catch (Exception e) {}
                    break;

            }
        }
    };
}
