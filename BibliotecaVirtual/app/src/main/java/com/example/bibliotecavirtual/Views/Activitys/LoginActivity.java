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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login,sign;
    ProgressDialog dialog;
    public String responseString = null;
    ConnectionDetector cn;
    Protocol protocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cn= new ConnectionDetector(this);
        protocol = new Protocol();

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        login = (Button) findViewById(R.id.button_login);
        sign = (Button) findViewById(R.id.button_sign_up);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cn.isConnectingToInternet()){
                    new loginTask().execute(true);
                }else {
                    Toast.makeText(getApplicationContext(),"Easy Note - Su dispositivo no cuenta con conexión a internet.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    class loginTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            dialog = ProgressDialog.show(LoginActivity.this, "", "Cargando", true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Boolean... booleans) {
            JSONObject postData = new JSONObject();
            JSONObject response=null;
            try {
                postData.put("userName", username.getText().toString());
                postData.put("contra", password.getText().toString());

                response = protocol.postJson(ConstValue.URL_LOGIN,responseString,postData);
                System.out.println("MI JSON EVENT: " + response.getString("estado"));

                if (response.getString("estado").equalsIgnoreCase("ok")) {
                    System.out.println("Adentro ");
                    JSONObject json = new JSONObject();
                    json = protocol.getJson(ConstValue.URL_GET_DOCUMENTS);
                    System.out.println("Mis documentos: " + json);


                }else {
                    Toast.makeText(getApplicationContext(),"Credenciales invalidad", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }
            // TODO Auto-generated method stub
            dialog.dismiss();
        }

    }

}
