package com.example.bibliotecavirtual.Views.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.ConnectionDetector;
import com.example.bibliotecavirtual.Utils.Protocol;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Button register, cancel;
    EditText name, email,pass, university;
    ProgressDialog dialog;
    public String responseString = null;
    Protocol protocol;
    ConnectionDetector cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cn= new ConnectionDetector(this);
        protocol = new Protocol();

        name = (EditText) findViewById(R.id.et_first_name);
        email = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_pass);
        university = (EditText) findViewById(R.id.et_university);
        university.setText("UNSA");

        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cn.isConnectingToInternet()){
                    new registerTask().execute(true);
                }else {
                    Toast.makeText(getApplicationContext(),"Easy Note - Su dispositivo no cuenta con conexión a internet.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel = (Button) findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    class registerTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            dialog = ProgressDialog.show(RegisterActivity.this, "", "Cargando", true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Boolean... booleans) {
            HttpURLConnection httpURLConnection = null;
            try{
                JSONObject postData = new JSONObject();
                postData.put("userName", name.getText().toString());
                postData.put("contra", pass.getText().toString());
                postData.put("correo", email.getText().toString());

                JSONObject response = protocol.postJson(ConstValue.URL_SEND_USER,responseString,postData);
                System.out.println("MI JSON EVENT: " + response);

            }
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                responseString = "Easy Note - Error al recuperar la información del portal.";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                Toast.makeText(getApplicationContext(), "Easy Note " + result, Toast.LENGTH_LONG).show();
            } else {
                if (ConstValue.getResponse().equals("200")) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ConstValue.setResponse("0");
                    startActivity(intent);
                    finish();
                }
            }
            // TODO Auto-generated method stub
            dialog.dismiss();
        }

    }

}
