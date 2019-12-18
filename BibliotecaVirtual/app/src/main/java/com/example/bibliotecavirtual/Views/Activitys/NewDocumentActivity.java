package com.example.bibliotecavirtual.Views.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Models.TemaClass;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.Protocol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
//import org.apache.commons.codec.binary.Base64;

public class NewDocumentActivity extends AppCompatActivity {
    ActionBar actionBar;
    Button subir, cancelar;
    EditText nombre;
    Spinner tema;
    ArrayList<TemaClass> temaClassArrayList = new ArrayList<TemaClass>();
    String idTema;
    Protocol protocol;
    int VALOR_RETORNO = 1;
    public Uri selectedPDF;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_document);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);

        protocol= new Protocol();

        nombre = (EditText) findViewById(R.id.et_documento_name);

        tema = (Spinner) findViewById(R.id.spnr_temas);
        temaClassArrayList = SqliteClass.getInstance(getApplicationContext()).databasehelp.temasql.getAllItem();
        ArrayAdapter<TemaClass> adapter =
                new ArrayAdapter<TemaClass>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, temaClassArrayList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        tema.setAdapter(adapter);

        tema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTema=SqliteClass.getInstance(getApplicationContext()).databasehelp.temasql.getIdTema((parent.getItemAtPosition(position)).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        subir = (Button) findViewById(R.id.button_share);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new subirTask().execute(true);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context applicationContext = MainActivity.getContextOfApplication();
        if (resultCode == RESULT_OK)
        {
            selectedPDF = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            System.out.println("URI : " + selectedPDF);

            Cursor cursor = applicationContext.getContentResolver().query(selectedPDF,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            cursor.close();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(NewDocumentActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent intent = new Intent(NewDocumentActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    class subirTask extends AsyncTask<Boolean, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(NewDocumentActivity.this, "", "Subiendo", true);
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Boolean... booleans) {
            String encodedBase64 = null;
            String responseString = null;
            String name_ = nombre.getText().toString();
            JSONObject response=null;

            try {
                FileInputStream inputStream = (FileInputStream) getContentResolver().openInputStream(selectedPDF);
                byte[] bytes = new byte[1000000];
                inputStream.read(bytes);
                encodedBase64 = new String(Base64.getEncoder().encode(bytes));
                System.out.println("documento en base 64: " + new String(Base64.getEncoder().encode(bytes)));

                JSONObject jsonDocument = new JSONObject();
                LocalDateTime locaDate = LocalDateTime.now();
                Date fecha = new Date();
                String date= fecha.getDay()+"/"+fecha.getMonth()+"/"+fecha.getYear();
                jsonDocument.put("nombre",nombre.getText().toString());
                jsonDocument.put("contador",0);
                jsonDocument.put("fecha",date);
                jsonDocument.put("codUsuario", SqliteClass.getInstance(getApplicationContext()).databasehelp.usersql.getID());
                jsonDocument.put("codTema",idTema);
                jsonDocument.put("codigoBase64Redu",new String(Base64.getEncoder().encode(bytes)));

                response = protocol.postJson(ConstValue.URL_GET_DOCUMENTS,responseString,jsonDocument);
                Toast.makeText(getApplicationContext(), "Archivo Subido con exito", Toast.LENGTH_SHORT).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }
}
