package com.example.caloriesnculture;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class culture01_01RegisterActivity_insert extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings){
        try {
            String str;
            //conn.setRequestMethod("POST");//POST는 숨겨서 보냄,GET은 걍 나는 모든걸 다 내 비칠 자신이 있다by이재민
            URL url = new URL("http://106.241.33.158:1080/culture01_01androidDB_insert.jsp");
            //URL url = new URL("http://10.0.2.2:8080/culture01_01/culture01_01androidDB_insert.jsp");;//바꿔주세요//http://192.168.56.1:8080/Cap_Connection_2/login_pra.jsp
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");


            //전송할 데이터. GET방식으로 작성
            sendMsg = "mno=" + strings[0] + "&mname=" + strings[1] + "&mpeople=" + strings[2] + "&mgenre=" + strings[3] + "&mreview=" + strings[4] + "&muserid=" + strings[5];
            System.out.println(sendMsg);
            //sendMsg = "?msg=" + strings[0];
            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                //통신 실패
                System.out.println("통신실패");
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //jsp로부터 받은 리턴값
        return receiveMsg;

    }
}