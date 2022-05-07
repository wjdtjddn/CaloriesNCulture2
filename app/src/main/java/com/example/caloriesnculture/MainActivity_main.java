package com.example.caloriesnculture;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity_main extends Activity{
    TextView nickname;
    String nickdata="";
    SharedPreferences pref;
    SharedPreferences pref2;
    SharedPreferences.Editor editor2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname=(TextView)findViewById(R.id.txt_main_name);
        // Intent login_intent=getIntent();
        // nickdata =login_intent.getStringExtra("nickname");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        nickname.setText(nickdata);

        Button btn_logout=findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                pref2 = getSharedPreferences("autoFile", MODE_PRIVATE);
                editor2 = pref2.edit();
                editor2.remove("id");
                editor2.remove("pw");
                editor2.commit();
                Intent intent_btn_logout=new Intent(MainActivity_main.this, MainActivity_login.class);
                startActivity(intent_btn_logout);
                finish();
            }
        });

        Button btn_main_calorie=findViewById(R.id.btn_main_calorie);
        btn_main_calorie.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_main_calorie=new Intent(MainActivity_main.this, MainActivity_cal_main.class);
                //intent_btn_main_calorie.putExtra("nickname2",nickdata);
                startActivity(intent_btn_main_calorie);
                finish();
            }
        });


        Button btn_edit=findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_edit=new Intent(MainActivity_main.this, MainActivity_join_edit.class);
                startActivity(intent_btn_edit);
                finish();
            }
        });
        Button btn_main_culture=findViewById(R.id.btn_main_culture);
        btn_main_culture.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_main_culture=new Intent(MainActivity_main.this, MainActivity_culture_main.class);
                //intent_btn_main_culture.putExtra("nickname3",nickdata);
                startActivity(intent_btn_main_culture);
                finish();
            }
        });

    }

}
