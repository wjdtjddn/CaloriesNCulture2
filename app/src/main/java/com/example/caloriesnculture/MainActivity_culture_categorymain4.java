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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity_culture_categorymain4 extends AppCompatActivity {
    ListView listview_categorymain4_1;
    LinearLayout categorymain4_item;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    private AdapterCultureCategory4 adapter;
    private PostList postList = new PostList();

    private Post selectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_categorymain4);

        adapter = new AdapterCultureCategory4(this, postList);

        listview_categorymain4_1 = (ListView) findViewById(R.id.listview_categorymain4_1);
        listview_categorymain4_1.setAdapter(adapter);

        Button btn_culture_categorymain4_search = findViewById(R.id.btn_culture_categorymain4_search);
        btn_culture_categorymain4_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                    String nickdata = pref.getString("nickname","error");

                    EditText editText = (EditText)findViewById(R.id.txt_culture_categorymain4_search);
                    String json = new MainActivity_culture_categorymain4.CustomTask().execute("get_post_by_title", editText.getText().toString(), nickdata).get();

                    PostList list = new PostList();
                    Gson gson = new Gson();
                    list = gson.fromJson(json, PostList.class);

                    adapter.mDatas.clear();
                    if (list.posts.size() == 0) {
                        //.. TODO :: ???????????? ?????????
                        Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
                        for (Post post : list.posts) {
                            adapter.mDatas.add(post);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        Button btn_culture_categorymain4_plus = findViewById(R.id.btn_culture_categorymain4_plus);

        btn_culture_categorymain4_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain4_plus = new Intent(MainActivity_culture_categorymain4.this, MainActivity_culture_reviewwrite4.class);

                SavedPostData.getInstance().isNew = true;

                pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
                editor = pref.edit();
                editor.putString("postID", "-1");
                editor.commit();

                startActivityForResult(intent_btn_culture_categorymain4_plus, 2000);
            }
        });

        Button btn_culture_categorymain4_back = findViewById(R.id.btn_culture_categorymain4_back);

        btn_culture_categorymain4_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain4_back = new Intent(MainActivity_culture_categorymain4.this, MainActivity_culture_main.class);
                startActivity(intent_btn_culture_categorymain4_back);
            }
        });


        listview_categorymain4_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView,
                                    View view, int position, long id) {

                selectedPost = adapter.getItem(position);

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity_culture_categorymain4.this);
                alert_confirm.setMessage("??????????????? ??????????????????????").setCancelable(false).setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SavedPostData.getInstance().isNew = false;

                                Intent intent_selected_item = new Intent(MainActivity_culture_categorymain4.this, MainActivity_culture_reviewwrite4.class);
                                pref = getSharedPreferences("staticFILE", MODE_PRIVATE);
                                editor = pref.edit();
                                editor.putString("postID", selectedPost.id);
                                editor.commit();
                                startActivityForResult(intent_selected_item, 2000);
                                finish();

                            }

                        }).setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {

                                    Log.i("SELECT ELEM", selectedPost.id);
                                    //.. INFO :: ????????? ????????? ID??? JSP??? ?????????
                                    String json = new MainActivity_culture_categorymain4.CustomTask().execute("category4_del", selectedPost.id).get();

                                    Gson gson = new Gson();
                                    DeletePostResponse res = gson.fromJson(json, DeletePostResponse.class);

                                    Log.i("DELETE RES", json);

                                    if (res.code.equals("200")) {
                                        Toast.makeText(MainActivity_culture_categorymain4.this, "????????????", Toast.LENGTH_SHORT).show();

                                        //.. ????????? ID??? ????????? ??????
                                        int pos = adapter.mDatas.indexOf(selectedPost);
                                        adapter.mDatas.remove(pos);

                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(MainActivity_culture_categorymain4.this, "error", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                        });

                AlertDialog alert = alert_confirm.create();
                alert.show();

            }
        });


        try {

            pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
            String nickdata = pref.getString("nickname","error");
            String json = new MainActivity_culture_categorymain4.CustomTask().execute("get_all_post", nickdata).get();

            PostList list = new PostList();
            Gson gson = new Gson();
            list = gson.fromJson(json, PostList.class);

            if (list.posts.size() == 0) {
                //.. TODO :: ???????????? ?????????
                Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
                for (Post post : list.posts) {
                    adapter.mDatas.add(post);
                }

                adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2000: {
                    try {

                        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                        String nickdata = pref.getString("nickname","error");
                        String json = new MainActivity_culture_categorymain4.CustomTask().execute("get_all_post", nickdata).get();

                        PostList list = new PostList();
                        Gson gson = new Gson();
                        list = gson.fromJson(json, PostList.class);

                        if (list.posts.size() == 0) {
                            //.. TODO :: ???????????? ?????????
                            Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(MainActivity_culture_categorymain4.this, "??????ok", Toast.LENGTH_SHORT).show();
                            for (Post post : list.posts) {
                                adapter.mDatas.add(post);
                            }

                            adapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                String typeStr = strings[0];

                String str;
                URL url = new URL("http://106.241.33.158:1080/culture04_01.jsp");//???????????????
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "type=" + strings[0];
                //.. type??? ?????? JSP??? ???????????? ????????? ????????? ?????????
                if (typeStr == "category4_del") {
                    sendMsg += "&number=" + strings[1];
                }else if(typeStr == "get_post_by_title") {
                    sendMsg += "&word=" + strings[1] + "&user=" + strings[2];
                }else if(typeStr == "get_all_post")
                {
                    sendMsg += "&user=" + strings[1];
                }
                //sendMsg = "type="+strings[0]+"&user="+strings[1]+"&date="+strings[2]+"&gerne="+strings[3]+"&title="+strings[4]+"&food="+strings[5]+"&content="+strings[6]+"&auth="+strings[7];
                //number = ??????????????? user = ????????? date = ?????? gerne = ??????4?????? ?????? title = ?????? food = ??????,  content = ?????? auth = ??????
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("?????? ??????", conn.getResponseCode() + "??????");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}

class Post{
    String id;
    String title;
    String content;
}

class PostList {
    ArrayList<Post> posts = new ArrayList<Post>();
}

class DeletePostResponse {
    int postId;
    String code;
}