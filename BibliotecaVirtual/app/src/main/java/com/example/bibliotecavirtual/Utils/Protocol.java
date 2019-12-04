package com.example.bibliotecavirtual.Utils;

import android.util.Log;

import com.example.bibliotecavirtual.Config.ConstValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Protocol {
    public JSONObject postJson(String url, String responseString, JSONObject postData){
        BufferedReader reader = null;
        JSONObject objReturn = new JSONObject();
        HttpURLConnection httpURLConnection =null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8")); tl ony pe
            os.writeBytes(postData.toString());
            os.flush();

            InputStream stream = httpURLConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);

            }

            os.close();
            stream.close();
            String json = buffer.toString();
            objReturn = new JSONObject(json);

            ConstValue.setResponse(String.valueOf(httpURLConnection.getResponseCode()));

            Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
            Log.i("MSG" , httpURLConnection.getResponseMessage());

            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objReturn;

    }
    public JSONObject getJson(String url){
        JSONObject jsonResult= null;

        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            String json = sb.toString();
            jsonResult = new JSONObject(json);


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonResult;

    }
}
