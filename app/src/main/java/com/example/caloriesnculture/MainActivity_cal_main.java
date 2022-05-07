package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_cal_main extends AppCompatActivity {

    TextView nickname;
    String nickdata="";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_cal_main.this, MainActivity_main.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_main);

        nickname=(TextView)findViewById(R.id.txt_calmain_name);

        //Intent cal_main_intent=getIntent();
        //nickdata=cal_main_intent.getStringExtra("nickname2");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        nickname.setText(nickdata);

        /*Button btn_calmain_menu=findViewById(R.id.btn_calmain_menu);
        btn_calmain_menu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_btn_calmain_menu=new Intent(MainActivity_cal_main.this, MainActivity_main.class);
                startActivity(intent_btn_calmain_menu);
            }
        });*/

        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                String date="" + year + "/" + (month + 1) + "/" + dayOfMonth;
                Toast.makeText(MainActivity_cal_main.this, date,Toast.LENGTH_LONG ).show();
                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                editor=pref.edit();
                editor.putString("inputdate_cal",date);
                editor.commit();
                Intent intent= new Intent(MainActivity_cal_main.this, MainActivity_cal_day.class);
                //intent.putExtra("today",date);
                //intent.putExtra("nick",nickdata);
                startActivity(intent);
                finish();
            }
        });


    }
}
