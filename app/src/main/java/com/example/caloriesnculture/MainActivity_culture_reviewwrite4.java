package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity_culture_reviewwrite4 extends AppCompatActivity {
    EditText txt_culture_review4_title, txt_culture_review4_author, txt_culture_review4_content;
    TextView txt_culture_review4_genre;
    Button btn_culture_review4_save;
    private TextView txt_culture_review4_date;
    SharedPreferences pref;
    String nickdata="";

    private String feelValStr = "";// GENRE_VAL

    SharedPreferences.Editor editor;



    public class DataResult
    {
        int id;
        String pkUID;
        String date;
        int gerne;
        String title;
        int food;
        String content;
        String auth;
    }

    private Post curPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_reviewwrite4);

        txt_culture_review4_title = (EditText) findViewById(R.id.txt_culture_review4_title);
        txt_culture_review4_date = (TextView) findViewById(R.id.txt_culture_review4_date);
        txt_culture_review4_author = (EditText) findViewById(R.id.txt_culture_review4_author);
        txt_culture_review4_content= (EditText) findViewById(R.id.txt_culture_review4_content);
        txt_culture_review4_genre= (TextView) findViewById(R.id.txt_culture_review4_genre);
        btn_culture_review4_save = (Button) findViewById(R.id.btn_culture_review4_save);
        btn_culture_review4_save.setOnClickListener(btnListener);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        String postId = pref.getString("postID", "-1");
        nickdata=pref.getString("nickname","error");

        Intent intent = getIntent();
        curPost = SavedPostData.getInstance().GetPostData();
        if(SavedPostData.getInstance().isNew)
        {
            String datePattern = "yyyy/MM/dd";
            Date curDate = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            String dateStr = dateFormatter.format(curDate.getTime());

            //.. TODO :: ??????
            feelValStr = String.valueOf(intent.getIntExtra("FEEL_VAL", 0));

            txt_culture_review4_date.setText(dateStr);
            txt_culture_review4_date.setFocusable(true);

            //.. TODO :: UI ?????????
            if(SavedPostData.getInstance().GetPostData() == null)
            {
                SavedPostData.getInstance().Init();
            }
        }
        else
        {
            //.. TODO :: ??????
            String json  = null;
            try {

                json = new CustomTask().execute("get_post_detail", postId).get();

                Log.i("RES => ", json);

                Gson gson = new Gson();
                DataResult res = gson.fromJson(json, DataResult.class);

                //.. TODO :: UI DataResult ?????? ???????????? ?????????


                String[] splitDateStr = res.date.split(" ");


                txt_culture_review4_title.setText(res.title);
                txt_culture_review4_author.setText(res.auth);
                txt_culture_review4_content.setText(res.content);
                txt_culture_review4_date.setText(splitDateStr[0]);
                txt_culture_review4_date.setFocusable(true);

                feelValStr = String.valueOf(res.gerne);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] feels = new String[] { "", "??????", "?????????", "??????", "????????????" };
        txt_culture_review4_genre.setText(feels[Integer.parseInt(feelValStr)]);

        Button btn_culture_review4_genre = findViewById(R.id.btn_culture_review4_genre);
        btn_culture_review4_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_review4_genre = new Intent(MainActivity_culture_reviewwrite4.this, MainActivity_culture_selectDis4.class);
                startActivityForResult(intent_btn_culture_review4_genre, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {
                    int feelVal = data.getIntExtra("FEEL_VAL", 0);
                    String[] feels = new String[]{"", "??????", "?????????", "??????", "????????????"};
                    txt_culture_review4_genre.setText(feels[feelVal]);
                    feelValStr = String.valueOf(feelVal);
                }
                break;
            }
        }
    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/culture04_01.jsp");//???????????????
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                String typeStr = strings[0];
                sendMsg = "type="+typeStr;
                if(typeStr == "get_post_detail")
                {
                    sendMsg += "&post_id="+strings[1];
                }
                else if(typeStr == "update_post") {
                    sendMsg += "&title="+strings[1]+"&number="+strings[2]+"&date="+strings[3]+"&content="+strings[4]+"&gerne="+strings[5]+"&auth="+strings[6];
                }
                else
                {
                    sendMsg += "&number="+strings[1]+"&user="+strings[2]+"&date="+strings[3]+"&gerne="+strings[4]+"&title="+strings[5]+"&food="+strings[6]+"&content="+strings[7]+"&auth="+strings[8];
                }
                //number = ??????????????? user = ????????? date = ?????? gerne = ??????4?????? ?????? title = ?????? food = ??????,  content = ?????? auth = ??????
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
                    Log.i("?????? ??????", conn.getResponseCode()+"??????");
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
        public void onClick(View view1) {

            switch (view1.getId()) {
                case R.id.btn_culture_review4_save : // ?????? ?????? ????????? ??????

                    pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                    String postId = pref.getString("postID", "-1");
                    if(SavedPostData.getInstance().isNew)
                    {
                        //.. ????????????
                        String datePattern = "yyyy/MM/dd";
                        Date curDate = new Date();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                        String dateStr = dateFormatter.format(curDate.getTime());

                        String rowCnt = "0";
                        String user = nickdata;
                        String StringToDate = txt_culture_review4_date.toString();
                        String gerne = "4";
                        String title = txt_culture_review4_title.getText().toString();
                        String food = feelValStr;//String food = Integer.parseInt(food); // ?????? ??????????????? ???????????? ????????????
                        String content = txt_culture_review4_content.getText().toString();
                        String auth = txt_culture_review4_author.getText().toString();
                        try {
                            String json  = new CustomTask().execute("save_post", rowCnt, user, dateStr, gerne, title, food, content, auth, "????????????").get();
                            Log.i("RES => ", json);

                            Gson gson = new Gson();
                            DataResult res = gson.fromJson(json, DataResult.class);



                            Toast.makeText(MainActivity_culture_reviewwrite4.this,"????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                            Intent btn_culture_review4_save= new Intent(MainActivity_culture_reviewwrite4.this, MainActivity_culture_categorymain4.class);
                            //startActivity(btn_culture_review4_save);

                            finish();
                        }catch (Exception e) {
                            Log.e("Error => ", e.getMessage());
                        }
                    }
                    else
                    {
                        //.. ??????
                        String datePattern = "yyyy/MM/dd";
                        Date curDate = new Date();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                        String dateStr = dateFormatter.format(curDate.getTime());

                        String StringToDate = txt_culture_review4_date.toString();
                        String gerne = "4";
                        String title = txt_culture_review4_title.getText().toString();
                        String food = feelValStr;
                        String content = txt_culture_review4_content.getText().toString();
                        String auth = txt_culture_review4_author.getText().toString();
                        try {
                            String json  = new CustomTask().execute("update_post", title, postId, dateStr, content, food, auth, "????????????").get();
                            Log.i("RES => ", json);

                            Toast.makeText(MainActivity_culture_reviewwrite4.this,"????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                            Intent btn_culture_review4_save= new Intent(MainActivity_culture_reviewwrite4.this, MainActivity_culture_categorymain4.class);
                            setResult(RESULT_OK, btn_culture_review4_save);

                            pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putString("postID", "-1");
                            editor.commit();

                            finish();
                        }catch (Exception e) {
                            Log.e("Error => ", e.getMessage());
                        }
                    }


                    break;
            }
        }
    };
}