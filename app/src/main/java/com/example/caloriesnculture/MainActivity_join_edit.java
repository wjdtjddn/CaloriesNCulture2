package com.example.caloriesnculture;

import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.StringTokenizer;

// String a="2007";
//        int num1=adspin1.getPosition(a);
//        sp.setSelection(num1);
/*
        rb2_fem2.setChecked(true);*/


public class MainActivity_join_edit extends AppCompatActivity {
    EditText join_id, join_pw,txt_find_name_id3;
    Button btn_join,join_idbtn;
    RadioGroup rg;RadioButton rb1_man2,rb2_fem2;
    Button btn_pwdcheck;

    ArrayAdapter<CharSequence> adspin1,adspin2,adspin3,adspin4;
    Spinner sp; String text="";//년도변수
    Spinner sp2; String text2="";//년도변수
    Spinner sp3; String text3="";//년도변수
    Spinner sp4; String text4="";//년도변수
    EditText txt_find_phone5,txt_find_phone6;

    String rbtext="";

    String nickdata="";


    SharedPreferences pref;

    String pwpw="";
    String phone1="";String phone2="";String phone3="";
    String na="";
    String gen="";
    String bir="";
    String hei="";
    String wei="";

    @Override
    public void onBackPressed() {
        Intent intent_btn_calsearch_home3=new Intent(MainActivity_join_edit.this, MainActivity_main.class);
        startActivity(intent_btn_calsearch_home3);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_edit);



        btn_pwdcheck=(Button)findViewById(R.id.btn_pwdcheck2);
        btn_pwdcheck.setOnClickListener(btnListener1);
        rg = (RadioGroup)findViewById(R.id.radioGroup2);
        rb1_man2=(RadioButton)findViewById(R.id.rb1_man2);
        rb2_fem2=(RadioButton)findViewById(R.id.rb2_fem2);


        txt_find_name_id3=(EditText)findViewById(R.id.txt_find_name_id3);
        txt_find_phone5=(EditText)findViewById(R.id.txt_find_phone5);
        txt_find_phone6=(EditText)findViewById(R.id.txt_find_phone6);

        sp = (Spinner)findViewById(R.id.spinner_birth2);

        sp2 = (Spinner)findViewById(R.id.spinner_height3);
        sp3 = (Spinner)findViewById(R.id.spinner_weight2);
        sp4=(Spinner)findViewById(R.id.spinner_Phone_find3);

        join_id = (EditText) findViewById(R.id.join_id2);
        join_idbtn = (Button) findViewById(R.id.join_idbtn2);
        join_idbtn.setOnClickListener(btnListener1);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        join_id.setText(nickdata);
        join_id.setClickable(false);
        join_id.setFocusable(false);
        join_idbtn.setEnabled(false);
        join_pw = (EditText) findViewById(R.id.join_pw2);
        btn_join = (Button) findViewById(R.id.btn_join2);
        btn_join.setOnClickListener(btnListener1);






        adspin1 = ArrayAdapter.createFromResource(this, R.array.birth, android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adspin1);

        adspin2 = ArrayAdapter.createFromResource(this, R.array.height, android.R.layout.simple_spinner_dropdown_item);

        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adspin2);

        adspin3 = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_dropdown_item);

        adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adspin3);

        adspin4 = ArrayAdapter.createFromResource(this, R.array.number_Phone, android.R.layout.simple_spinner_dropdown_item);

        adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp4.setAdapter(adspin4);


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

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text2=adspin2.getItem(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text3=adspin3.getItem(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                text4=adspin4.getItem(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String joinid = join_id.getText().toString();


        //비밀번호+전화번호+이름+성별+생년+키+몸무게
        try {

            String result = new CustomTask().execute(joinid, "0", "0", "0", "0", "0","0","0", "join_info").get();
            if (result.equals("ok")) {
                Toast.makeText(MainActivity_join_edit.this, result + "연결ok", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity_join_edit.this, result + "연결ok", Toast.LENGTH_SHORT).show();

                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();
                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }
                String a=array[1];
                StringTokenizer st1 = new StringTokenizer(a, "-");
                int n1 = st1.countTokens();
                String[] array1 = new String[st1.countTokens()];
                int k = 0;
                while (st1.hasMoreElements()) {
                    array1[k++] = st1.nextToken();
                }
                //값 받아오기
                pwpw=array[0];
                phone1=array1[0]; phone2=array1[1]; phone3=array1[2];
                na=array[2];
                gen=array[3];
                bir=array[4];
                hei=array[5];
                wei=array[6];



            }

        }catch (Exception e) {}

        //에딧텍스트 값 설정
        txt_find_phone5.setText(phone2);
        txt_find_phone6.setText(phone3);
        join_pw.setText(pwpw);
        txt_find_name_id3.setText(na);
        //스피너 값설정
        int num1=adspin1.getPosition(bir);
        sp.setSelection(num1);
        int num2=adspin2.getPosition(hei);
        sp2.setSelection(num2);
        int num3=adspin3.getPosition(wei);
        sp3.setSelection(num3);
        int num4=adspin4.getPosition(phone1);
        sp4.setSelection(num4);
        //라디오 버튼 값 설정
        if(gen.equals("남자")){
            rb1_man2.setChecked(true);
        }else{
            rb2_fem2.setChecked(true);
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
                URL url = new URL("http://106.241.33.158:1080/join.jsp");//바꿔주세요
                // http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // 192.168.56.1
                //"http://106.241.33.158:1080/join.jsp"
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "id="+strings[0]+"&pwd="+strings[1]+"&gender="+strings[2]+"&birth="+strings[3]+"&height="+strings[4]+"&weight="+strings[5]+"&name="+strings[6]+"&phone="+strings[7]+"&type="+strings[8];
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
                case R.id.btn_join2 : // 회원수정

                    int id = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    rbtext=rb.getText().toString();
                    String joinid = join_id.getText().toString();
                    String joinpwd = join_pw.getText().toString();
                    String name=txt_find_name_id3.getText().toString();
                    String phone=text4+"-"+txt_find_phone5.getText().toString()+"-"+txt_find_phone6.getText().toString();


                    try {
                        //항목이 하나도 안눌리면 확인할 것.
                        /*if(joinid.equals("")==true||joinpwd.equals("")==true||rbtext.equals("")==true||text.equals("")==true||text2.equals("")==true||text3.equals("")==true){
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_join.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("항목들을 모두 채워주세요");
                            alert.show();
                        }*/

                        if (btn_pwdcheck.isEnabled() == false && join_idbtn.isEnabled() == false) {

                            String result = new CustomTask().execute(joinid, joinpwd, rbtext, text, text2, text3,name,phone, "join_edit").get();
                            if (result.equals("idd")) {
                                Toast.makeText(MainActivity_join_edit.this, joinid + joinpwd + rbtext + text + text2 + text3, Toast.LENGTH_SHORT).show();
                                join_id.setText("");
                                join_pw.setText("");
                            } else if (result.equals("ok")) {

                                join_id.setText("");
                                join_pw.setText("");
                                Toast.makeText(MainActivity_join_edit.this, "회원수정을 완료했습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent_btn_join = new Intent(MainActivity_join_edit.this, MainActivity_login.class);
                                startActivity(intent_btn_join);
                                finish();
                            }
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_join_edit.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("비밀번호 체크를 진행해주세요");
                            alert.show();
                        }


                    }catch (Exception e) {}
                    break;


                case R.id.btn_pwdcheck2 : // 중복체크

                    String joinpwd_check= join_pw.getText().toString();


                    try {

                        String result  = new CustomTask().execute("0",joinpwd_check,"0","0","0","0","0","0","pwdcheck").get();

                        if(result.equals("okok")) {
                            Toast.makeText(MainActivity_join_edit.this,"확인되었습니다",Toast.LENGTH_SHORT).show();
                            btn_pwdcheck.setEnabled(false);
                        }

                        else if(result.equals("tryagain")) {

                            // Toast.makeText(MainActivity_join.this,"pw는 1개이상의 ",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_join_edit.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("pw는 1개이상의 소문자와 대문자, 특수문자를 포함해 5-8글자입니다.");
                            alert.show();

                            join_pw.setText("");
                        }
                    }catch (Exception e) {}
                    break;
            }
        }
    };
}
