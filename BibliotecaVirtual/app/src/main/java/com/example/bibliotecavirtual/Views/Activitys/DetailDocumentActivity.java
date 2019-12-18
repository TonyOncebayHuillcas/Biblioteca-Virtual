package com.example.bibliotecavirtual.Views.Activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.Protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailDocumentActivity extends AppCompatActivity {
    ActionBar actionBar;
    TextView title,autor,universidad,descargas;
    Button download;
    Protocol protocol;
    DocumentClass documentClass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        protocol =  new Protocol();

        download = (Button) findViewById(R.id.btn_share);

        title = (TextView) findViewById(R.id.tv_title);
        autor = (TextView) findViewById(R.id.tv_author);
        universidad = (TextView) findViewById(R.id.tv_univerisity);
        descargas = (TextView) findViewById(R.id.tv_count);

        title.setText(ConstValue.getNombre());
        autor.setText(ConstValue.getCodUsuario());
        universidad.setText("UNSA");
        descargas.setText(String.valueOf(ConstValue.getContador()));
        documentClass = new DocumentClass();

        download.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                new descargaTask().execute(true);
            }
        });
    }

    class descargaTask extends AsyncTask<Boolean, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            dialog = ProgressDialog.show(DetailDocumentActivity.this, "", "Descargando", true);
            super.onPreExecute();
        }


        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Boolean... booleans) {


            JSONObject jsonDoc = new JSONObject();
            String idDoc = ConstValue.getIdDoc();

            jsonDoc = protocol.getJson(ConstValue.URL_GET_DOCUMENTS+"/"+idDoc);
            System.out.println("Documento " + jsonDoc);
            try {
                File file = new File("/storage/emulated/0/Download/"+ConstValue.getNombre()+".pdf");
                FileOutputStream fos = new FileOutputStream(file);
                JSONObject dc1 = jsonDoc.getJSONObject("documento");
                System.out.println("archivo 64 " + dc1.getString("archivo"));
                byte[] decoder = Base64.getDecoder().decode(dc1.getString("archivo"));
                fos.write(decoder);
                JSONObject putJson = new JSONObject();
                putJson.put("contador",2);
                protocol.putJson(ConstValue.URL_UPDATE_DOCUMET+"/"+idDoc,putJson);
                System.out.println("PDF File Saved");

                int cont = SqliteClass.getInstance(getApplicationContext()).databasehelp.documentsql.getContador(dc1.getString("_id"));
                System.out.println("Contador :"+cont);
                SqliteClass.getInstance(getApplicationContext()).databasehelp.documentsql.updateContador(dc1.getString("contador"),cont);

                System.out.println("Contador Nuevo :"+SqliteClass.getInstance(getApplicationContext()).databasehelp.documentsql.getContador(dc1.getString("_id")));
                //

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            /*
            if (result != null) {
                Toast.makeText(getApplicationContext(), "Easy Note " + result, Toast.LENGTH_LONG).show();
            } else {
                if (ConstValue.getResponse().equals("200")) {
                    Intent intent = new Intent(DetailDocumentActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }
            */
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "Archivo Descargado con exito", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(DetailDocumentActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent intent = new Intent(DetailDocumentActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
