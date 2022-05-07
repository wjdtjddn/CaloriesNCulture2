package com.example.caloriesnculture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity_culture_categorymain1 extends AppCompatActivity {
    private Button new_r;
    private String str1,str2,str3;//테스트2
    private String mname,people,review,genre,result;
    private ArrayList<MainActivity_culture_mlistview1> datali;
    private int no;
    ListView listview_categorymain_1;
    culture01_01ListviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_categorymain1);

        datali = new ArrayList<MainActivity_culture_mlistview1>();
        listview_categorymain_1 = findViewById(R.id.listview_categorymain_1);
        adapter = new culture01_01ListviewAdapter();
        listview_categorymain_1.setAdapter(adapter);

        culture01_01RegisterActivity_listview list = new culture01_01RegisterActivity_listview();//클래스 불러오기//2020-06-05 혹시나 장르추천 기능 도입시 넣을 부분
        String msg = "";
        try{
            //SharedPreferences pref2 = getSharedPreferences("autoFile", MODE_PRIVATE);
            SharedPreferences pref2 = getSharedPreferences("staticFILE", MODE_PRIVATE);
            //String id = pref2.getString("id",null);
            String id = pref2.getString("nickname",null);
            msg=list.execute(id).get();//msg는 받아오는 친구임 ㅇㅇ ㅇㅇ ㅇ ㅇ
        }catch(Exception e){
            e.printStackTrace();
        }
        msg = msg.trim();//공백제거
        msg = msg.substring(1);//문자열 자르기
        msg = msg.substring(0, msg.length()-1);

        System.out.println("arrar " + msg);

        String[] array = msg.split(", ");//문자열 자르기기

        System.out.println("array " + array.length);
        if(!(array.length == 1)) {
            for (int i = 0; i < array.length; i += 3) {//i값에 따라
                //adapter.(new listview(array[i],array[i+1]));//1
                adapter.addItem(array[i], array[i + 1], array[i + 2]);
                datali.add(new MainActivity_culture_mlistview1(array[i], array[i + 1], array[i + 2]));

            }
        }
//        for(int i = 0; i< 5; i++){
//            adapter.addItem("AAAA", Integer.toString(i),"test");
//        }


        listview_categorymain_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = getResources().getStringArray(R.array.choice);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_culture_categorymain1.this);
                builder.setTitle("AlertDialog Title");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        if("수정".equals(items[pos].toString())){
                            Intent intent = new Intent(MainActivity_culture_categorymain1.this, MainActivity_culture_reviewwrite_modify1.class);
                            intent.putExtra("mno", datali.get(position).getMnoli());
                            startActivity(intent);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_culture_categorymain1.this);

                            builder.setTitle("경 고 창").setMessage("정말 삭제할거야?");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    culture01_01RegisterActivity_delete delete = new culture01_01RegisterActivity_delete();
                                    try {
                                        delete.execute(datali.get(position).getMnoli()).get();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    finish();
                                    Intent de_in = new Intent(MainActivity_culture_categorymain1.this, MainActivity_culture_categorymain1.class);
                                    de_in.putExtra("mno", datali.get(position).getMnoli());
                                    startActivity(de_in);
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    }
                });
                builder.show();

            }
        });

        adapter.notifyDataSetChanged();//데이터 삽입 저장

        Button btn_culture_categorymain1_plus = findViewById(R.id.btn_culture_categorymain1_plus);

        btn_culture_categorymain1_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain1_plus = new Intent(MainActivity_culture_categorymain1.this, MainActivity_culture_reviewwrite1.class);
                startActivity(intent_btn_culture_categorymain1_plus);
            }
        });

        Button btn_culture_categorymain_back = findViewById(R.id.btn_culture_categorymain_back);

        btn_culture_categorymain_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_btn_culture_categorymain_back = new Intent(MainActivity_culture_categorymain1.this, MainActivity_culture_main.class);
                startActivity(intent_btn_culture_categorymain_back);
            }
        });
//        listview_categorymain_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//31번쨰 줄과 동일하게 2020-06-05 장르 추천시 넣을부분
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity_culture_categorymain1.this, MainActivity_culture_reviewwrite_modify1.class);
//                intent.putExtra("mno", position);
//
//                view.getContext().startActivity(intent);
//            }
//        });
    }
}
