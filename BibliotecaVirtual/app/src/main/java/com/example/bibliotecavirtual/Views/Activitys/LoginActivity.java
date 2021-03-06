package com.example.bibliotecavirtual.Views.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.Models.TemaClass;
import com.example.bibliotecavirtual.Models.UserClass;
import com.example.bibliotecavirtual.Models.UsersClass;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.ConnectionDetector;
import com.example.bibliotecavirtual.Utils.Protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login,sign;
    ProgressDialog dialog;
    public String responseString = null;
    ConnectionDetector cn;
    Protocol protocol;
    ArrayList<DocumentClass> loadDoc=null;
    DocumentClass documentClass;
    UserClass userClass;
    public boolean session= false;

    ArrayList<UsersClass> loadUsers = null;
    UsersClass usersClass;

    TemaClass temaClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cn= new ConnectionDetector(this);
        protocol = new Protocol();

        SharedPreferences sharedPref = getSharedPreferences("login_preferences",Context.MODE_PRIVATE);
        String active=sharedPref.getString("logueado","inactive");
        if(active.equals("active")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        login = (Button) findViewById(R.id.button_login);
        sign = (Button) findViewById(R.id.button_sign_up);
        username.setText("german");password.setText("1q2w3e");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cn.isConnectingToInternet()){
                    if(username.getText().length()==0 || password.getText().length()==0){
                        Toast.makeText(getApplicationContext(),"Easy Note - Tiene que ingresar un usuario y una contraseña",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new loginTask().execute(true);
                    }
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
                System.out.println("MI JSON EVENT: " + response);

                if (response.getString("estado").equalsIgnoreCase("ok")) {
                    ConstValue.setExistUser("ok");
                    System.out.println("Adentro ");

                    JSONObject json = new JSONObject();
                    JSONArray jsonArray = null;
                    json = protocol.getJson(ConstValue.URL_GET_USER+"/"+response.getString("_id"));
                    System.out.println("Usuario " +json);

                    JSONObject data = json.getJSONObject("usuario");
                    userClass = new UserClass(1,data.getString("_id"),data.getString("userName"),data.getString("correo"),data.getString("contra"),"5dd8bd19d099cc00047d2213");
                    SqliteClass.getInstance(getApplicationContext()).databasehelp.usersql.addUser(userClass);

                    JSONObject jsondoc = new JSONObject();
                    jsonArray = null;
                    jsondoc = protocol.getJson(ConstValue.URL_GET_DOCUMENTS);
                    System.out.println("Mis documentos: " + jsondoc);
                    JSONArray jsnArrayDoc = jsondoc.getJSONArray("documentos");

                    loadDoc= new ArrayList<DocumentClass>();
                    for(int j=0 ; j<jsnArrayDoc.length() ; j++){
                        JSONObject js = jsnArrayDoc.getJSONObject(j);
                        documentClass=  new DocumentClass(jsnArrayDoc.getJSONObject(j).getString("id"),jsnArrayDoc.getJSONObject(j).getString("nombre"),jsnArrayDoc.getJSONObject(j).getInt("contador"),jsnArrayDoc.getJSONObject(j).getString("fecha"),jsnArrayDoc.getJSONObject(j).getString("codTema"),jsnArrayDoc.getJSONObject(j).getString("codUsuario"));
                        SqliteClass.getInstance(getApplicationContext()).databasehelp.documentsql.addDocument(documentClass);

                        loadDoc.add(documentClass);
                    }

                    //tusuarios
                    JSONObject jsonusers = new JSONObject();
                    jsonArray = null;
                    jsonusers = protocol.getJson(ConstValue.URL_GET_USER);
                    System.out.println("Usuarios " + jsonusers);
                    JSONArray jsnArrayUser = jsonusers.getJSONArray("usuarios");
                    loadUsers= new ArrayList<UsersClass>();
                    for(int j=0 ; j<jsnArrayUser.length() ; j++){
                        JSONObject js = jsnArrayUser.getJSONObject(j);
                        usersClass=  new UsersClass(js.getString("_id"),js.getString("userName"));
                        SqliteClass.getInstance(getApplicationContext()).databasehelp.userssql.addUsers(usersClass);

                        loadUsers.add(usersClass);
                    }

                    //Temas
                    JSONObject jsontemas = new JSONObject();
                    jsonArray = null;
                    jsontemas = protocol.getJson(ConstValue.URL_GET_TEMAS);
                    System.out.println("Temas " + jsontemas);
                    JSONArray jsnArrayTemas = jsontemas.getJSONArray("temas");

                    for(int j=0 ; j<jsnArrayTemas.length() ; j++){
                        JSONObject js = jsnArrayTemas.getJSONObject(j);
                        temaClass = new TemaClass(js.getString("_id"),js.getString("nombre"));

                        SqliteClass.getInstance(getApplicationContext()).databasehelp.temasql.addTemas(temaClass);

                    }

                }else {
                    if (response.getString("estado").equalsIgnoreCase("error")){
                        ConstValue.setExistUser("error");
                        System.out.println("Error");
                    }

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
                if (ConstValue.getResponse().equals("200") && ConstValue.getExistUser().equals("ok")) {
                    SharedPreferences sharedPref = getSharedPreferences("login_preferences",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("logueado", "active");
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Easy Note - Datos incorrectos ingrese un usario registrdo o registre uno nuevo",Toast.LENGTH_SHORT).show();
                }
            }
            // TODO Auto-generated method stub
            dialog.dismiss();
        }

    }

}
