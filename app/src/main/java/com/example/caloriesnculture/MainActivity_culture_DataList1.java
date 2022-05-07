package com.example.caloriesnculture;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.caloriesnculture.R;

import java.util.ArrayList;

public class MainActivity_culture_DataList1 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private RecyclerView mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_data_list1);

        mlist = findViewById(R.id.txt_culture01_mlistView1);

        ArrayList<culture01_01listview> mdata = new ArrayList<>();
        mlist.setLayoutManager(new LinearLayoutManager(this)) ;

        //MainActivity_culture_recycleAdapter1 adapter = new MainActivity_culture_recycleAdapter1(mdata);
        //mlist.setAdapter(adapter);

        //값이 총 14개 들어가야됨
        SharedPreferences pref2 = getSharedPreferences("autoFile", MODE_PRIVATE);
        String id = pref2.getString("id",null);

        culture01_01RegisterActivity_listview mlist = new culture01_01RegisterActivity_listview();//클래스 불러오기
        String msg = "";
        try{
            msg=mlist.execute(id).get();//msg는 받아오는 친구임 ㅇㅇ ㅇㅇ ㅇ ㅇ
        }catch(Exception e){
            e.printStackTrace();
        }
        msg = msg.trim();//공백제거
        msg = msg.substring(1);//문자열 자르기
        msg = msg.substring(0, msg.length()-1);

        System.out.println("arrar " + msg);

        String[] array = msg.split(", ");//문자열 자르기기

        System.out.println("array " + array.length);
        for(int i=0;i<array.length;i+=2){//i값에 따라
            mdata.add(new culture01_01listview(array[i],array[i+1]));//1
        }

        //adapter.notifyDataSetChanged();//데이터 삽입 저장

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        TextView a = (TextView)findViewById(R.id.txt_culture01_mtitle);

        TextView tv =(TextView) arg1;

        a.setText("선택된 값 :" + tv.getText() + "\n선택된 id값:" +arg2);

    }
}
