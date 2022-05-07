package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_culture_date2 extends AppCompatActivity {

    TextView nickname;
    String nickdata="";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public void onBackPressed() {
        Intent intent_btn_calmain_menu=new Intent(MainActivity_culture_date2.this, MainActivity_culture_categorymain2.class);
        startActivity(intent_btn_calmain_menu);
        finish();
        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_date2);

        nickname=(TextView)findViewById(R.id.txt_calmain_name6);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");
        nickname.setText(nickdata);


        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView3);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                String date="" + year + "/" + (month + 1) + "/" + dayOfMonth;
                Toast.makeText(MainActivity_culture_date2.this, date,Toast.LENGTH_LONG ).show();
                pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
                editor=pref.edit();
                editor.putString("inputdate_culture",date);
                editor.commit();
                Intent intent= new Intent(MainActivity_culture_date2.this, MainActivity_culture_reviewwrite2.class);
                //intent.putExtra("today",date);
                //intent.putExtra("nick",nickdata);
                startActivity(intent);
                finish();
            }
        });


    }
}
