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

public class MainActivity_cal_searchplusd extends AppCompatActivity {

    String foodname_d="";
    String day="";
    String nickdata="";
    SharedPreferences pref;

    TextView txt_calsearchplus_foonamed2,txt_calsearchplusd_kcal2;
    EditText txt_calsearchplus5;
    Button btn_calsearchplusd_save2;

    ArrayAdapter<CharSequence> adspin1;
    Spinner sp; String text="";//년도변수


    PieChart piechart_searchplusd;
    String tansu="";
    String protein="";
    String fat="";
    @Override
    public void onBackPressed() {
        Intent intent_btn_calsearch_home3=new Intent(MainActivity_cal_searchplusd.this, MainActivity_cal_searchd.class);
        startActivity(intent_btn_calsearch_home3);
        finish();
        super.onBackPressed();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_searchplusd);

        piechart_searchplusd = (PieChart)findViewById(R.id.piechart_searchplusd);//객체를 생성해주고

        sp = (Spinner)findViewById(R.id.spinner_calsearchplus5);

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

        txt_calsearchplus_foonamed2=(TextView)findViewById(R.id.txt_calsearchplus_foonamed2);
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        foodname_d=pref.getString("foodname_d","error");
        txt_calsearchplus_foonamed2.setText(foodname_d);

        txt_calsearchplus5=(EditText)findViewById(R.id.txt_calsearchplus5);

        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        day=pref.getString("inputdate_cal","error");
        pref=getSharedPreferences("staticFILE",MODE_PRIVATE);
        nickdata=pref.getString("nickname","error");

        btn_calsearchplusd_save2=(Button)findViewById(R.id.btn_calsearchplusd_save2);
        btn_calsearchplusd_save2.setOnClickListener(btnListener1);

        txt_calsearchplusd_kcal2=(TextView)findViewById(R.id.txt_calsearchplusd_kcal2);
        try {
            String result = new CustomTask().execute("0", foodname_d,"0","0","dinner_kcal","0").get();

            if (result.equals("ok")) {
                Toast.makeText(MainActivity_cal_searchplusd.this, result + "연결ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity_cal_searchplusd.this, result + "연결ok", Toast.LENGTH_SHORT).show();
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
                txt_calsearchplusd_kcal2.setText(kcal_morning);

            }
        } catch (Exception e) {
        }


        piechart_searchplusd.setUsePercentValues(true);
        piechart_searchplusd.getDescription().setEnabled(false);
        piechart_searchplusd.setExtraOffsets(5,10,5,5);

        piechart_searchplusd.setDragDecelerationFrictionCoef(0.95f);

        piechart_searchplusd.setDrawHoleEnabled(false);
        piechart_searchplusd.setHoleColor(Color.WHITE);
        piechart_searchplusd.setTransparentCircleRadius(61f);

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


        /*Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);*/

        piechart_searchplusd.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"영양성분");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        piechart_searchplusd.setData(data);

    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg1, receiveMsg1;
        ResultSet rs;
        String sendMsg2;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://106.241.33.158:1080/AddFoodD.jsp");//바꿔주세요
                // "http://192.168.56.1:8080/Cap_Connection_2/join_pra.jsp"
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

                case R.id.btn_calsearchplusd_save2:

                    String search_m_g = txt_calsearchplus5.getText().toString();
                    //Toast.makeText(MainActivity_cal_searchplusm.this, search_mdnick+foodnamem+search_mdate+search_m_g, Toast.LENGTH_SHORT).show();
                    if(search_m_g.equals("")||search_m_g==null) {
                        /*AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity_cal_searchplusm.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("아이디와 비밀번호 체크를 진행해주세요");
                        alert.show();*/
                        Toast.makeText(MainActivity_cal_searchplusd.this, "입력된 데이터 없음", Toast.LENGTH_SHORT).show();
                        Intent intent_btn_calsearchplus_save = new Intent(MainActivity_cal_searchplusd.this, MainActivity_cal_searchm.class);
                        startActivity(intent_btn_calsearchplus_save);
                        finish();
                    }else{
                        try {
                            String result = new CustomTask().execute(nickdata,foodname_d,day,search_m_g,"searchd_add",text).get();

                            if (result.equals("ok")) {

                                Toast.makeText(MainActivity_cal_searchplusd.this, "연결ok", Toast.LENGTH_SHORT).show();
                                Intent intent_btn_calsearchplus_save=new Intent(MainActivity_cal_searchplusd.this, MainActivity_cal_searchd.class);
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
