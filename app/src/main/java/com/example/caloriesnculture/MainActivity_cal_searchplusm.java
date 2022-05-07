package com.example.caloriesnculture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity_cal_searchplusm extends AppCompatActivity {
    String foodname_m="";
    String day="";
    String nickdata="";
    TextView txt_foodnamem,txt_calsearchplus_kcal;
    EditText txt_calsearchplus1;
    Button btn_calsearchplus_save;
    SharedPreferences pref;
    PieChart piechart_searchplusm;
    String tansu="";
    String protein="";
    String fat="";

    ArrayAdapter<CharSequence> adspin1;
    Spinner sp; String text="";//년도변수

    @Override
    public void onBackPressed() {
        Intent intent_btn_calsearch_home3=new Intent(MainActivity_cal_searchplusm.this, MainActivity_cal_searchm.class);
        startActivity(intent_btn_calsearch_home3);
        finish();
        super.onBackPressed();

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_searchplusm);





        piechart_searchplusm = (PieChart)findViewById(R.id.piechart_searchplusm);//객체를 생성해주고




        sp = (Spinner)findViewById(R.id.spinner_calsearchplus1);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.kcal, android.R.layout.simple_spinner_dropdown_item);

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

        txt_calsearchplus1=(EditText)findViewById(R.id.txt_calsearchplus1);
        txt_foodnamem=(TextView)findViewById(R.id.txt_calsearchplus_fooname);
       // Intent searchplus_m=getIntent();
       // foodnamem =searchplus_m.getStringExtra("foodname");
       // search_mdate =searchplus_m.getStringExtra("search_mdate");
       // search_mdnick=searchplus_m.getStringExtra("search_mdnick");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        foodname_m=pref.getString("foodname_m","error");
        txt_foodnamem.setText(foodname_m);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_cal","error");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");

        btn_calsearchplus_save=findViewById(R.id.btn_calsearchplus_save);
        btn_calsearchplus_save.setOnClickListener(btnListener1);
        /*btn_calsearchplus_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               // Toast.makeText(MainActivity_cal_searchplusm.this,search_mdate , Toast.LENGTH_SHORT).show();


            }
        });*/

        txt_calsearchplus_kcal=(TextView)findViewById(R.id.txt_calsearchplus_kcal);
        try {
            String result = new CustomTask().execute("0", foodname_m,"0","0","morning_kcal","0").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_cal_searchplusm.this, result + "연결ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity_cal_searchplusm.this, result + "연결ok", Toast.LENGTH_SHORT).show();

                StringTokenizer st = new StringTokenizer(result, "&");
                int n = st.countTokens();
                String[] array = new String[st.countTokens()];
                int j = 0;
                while (st.hasMoreElements()) {
                    array[j++] = st.nextToken();
                }
                String a = "";
                String b = "";
                String stst = Integer.toString(n);

                String kcal_morning=array[0];
                tansu=array[1];
                protein=array[2];
                fat=array[3];
                txt_calsearchplus_kcal.setText(kcal_morning);

            }
        } catch (Exception e) {
        }


        piechart_searchplusm.setUsePercentValues(true);
        piechart_searchplusm.getDescription().setEnabled(false);
        piechart_searchplusm.setExtraOffsets(5,10,5,5);

        piechart_searchplusm.setDragDecelerationFrictionCoef(0.95f);

        piechart_searchplusm.setDrawHoleEnabled(false);
        piechart_searchplusm.setHoleColor(Color.WHITE);
        piechart_searchplusm.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        //Double tansu_double=Double.parseDouble(tansu);
        //Double protein_double=Double.parseDouble(protein);
        //Double fat_double=Double.parseDouble(fat);
        if(tansu.equals("0") && protein.equals("0")&&fat.equals("0")){
            yValues.add(new PieEntry((float) 100.0,"기타"));

        }else{
            yValues.add(new PieEntry((float) Double.parseDouble(tansu),"탄수화물"));
            yValues.add(new PieEntry((float) Double.parseDouble(protein),"단백질"));
            yValues.add(new PieEntry((float) Double.parseDouble(fat),"지방"));
        }

        /*Description description = n


        /*Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);*/

        piechart_searchplusm.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"영양성분");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        piechart_searchplusm.setData(data);


    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/AddFoodM.jsp");//바꿔주세요//"http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
                // "http://106.241.33.158:1080/join.jsp"
                //
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg1 = "search_mnick="+strings[0]+"&search_mname="+strings[1]+"&search_mdate="+strings[2]+"&search_mgram="+strings[3]+"&type="+strings[4]+"&gram="+strings[5];
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

                case R.id.btn_calsearchplus_save:

                    String search_m_g = txt_calsearchplus1.getText().toString();
                    //Toast.makeText(MainActivity_cal_searchplusm.this, search_mdnick+foodnamem+search_mdate+search_m_g, Toast.LENGTH_SHORT).show();
                    if(search_m_g.equals("")||search_m_g==null){
                        /*AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_cal_searchplusm.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("아이디와 비밀번호 체크를 진행해주세요");
                        alert.show();*/
                        Toast.makeText(MainActivity_cal_searchplusm.this, "입력된 데이터 없음", Toast.LENGTH_SHORT).show();
                        Intent intent_btn_calsearchplus_save=new Intent(MainActivity_cal_searchplusm.this, MainActivity_cal_searchm.class);
                        startActivity(intent_btn_calsearchplus_save);
                        finish();
                    }else{
                        try {
                            String result = new CustomTask().execute(nickdata,foodname_m,day,search_m_g,"search_add",text).get();

                            if (result.equals("ok")) {
                                Toast.makeText(MainActivity_cal_searchplusm.this, "연결ok", Toast.LENGTH_SHORT).show();
                                Intent intent_btn_calsearchplus_save=new Intent(MainActivity_cal_searchplusm.this, MainActivity_cal_searchm.class);
                                startActivity(intent_btn_calsearchplus_save);
                                finish();

                            }
                        } catch (Exception e) {
                        }
                    }
                    break;

            }
        }
    };
}
