package com.example.caloriesnculture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_culture_selectDis4 extends AppCompatActivity {

    RadioGroup rg;

    private int selectFeelValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_select_dis4);

        rg = (RadioGroup) findViewById(R.id.rg_select_genre4);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //RadioButton radioButton = (RadioButton) findViewById(checkedId);

                if(checkedId == R.id.rb_select_genre4_1){
                    selectFeelValue = 1;
                } else if(checkedId == R.id.rb_select_genre4_2){
                    selectFeelValue = 2;
                } else if (checkedId == R.id.rb_select_genre4_3) {
                    selectFeelValue = 3;
                } else if (checkedId == R.id.rb_select_genre4_4){
                    selectFeelValue = 4;
                }

                Log.i("SELECT FEEL VAL", String.valueOf(selectFeelValue));
            }
        });

        Button btn_culture_select_genre4 = findViewById(R.id.btn_culture_select_genre4);
        btn_culture_select_genre4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                //return data
                Intent data = new Intent(MainActivity_culture_selectDis4.this, MainActivity_culture_reviewwrite4.class);
                data.putExtra("FEEL_VAL", selectFeelValue);

                setResult(RESULT_OK, data);

                Log.i("SAVE FEEL VAL", String.valueOf(selectFeelValue));

                //startActivity(data);

                finish();
            }
        });
    }
}