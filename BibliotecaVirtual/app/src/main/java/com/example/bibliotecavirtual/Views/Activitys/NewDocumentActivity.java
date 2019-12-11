package com.example.bibliotecavirtual.Views.Activitys;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.Protocol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
//import org.apache.commons.codec.binary.Base64;

public class NewDocumentActivity extends AppCompatActivity {
    ActionBar actionBar;
    Button subir, cancelar;
    EditText nombre, tema;
    Protocol protocol;
    int VALOR_RETORNO = 1;
    public Uri selectedImage;
    public String path;
    private MenuItem item;
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
        tema= (EditText) findViewById(R.id.et_tema);

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
            selectedImage = data.getData();
            path =  data.getData().getPath();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            System.out.println("URI : " + selectedImage);
            System.out.println("URI -path : " + selectedImage.getPath());
            System.out.println("URI -getPath : " + path);

            Cursor cursor = applicationContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File file =new File(String.valueOf(selectedImage));
            File file1 =new File(String.valueOf(selectedImage.getPath()));
            cursor.close();
        }


    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
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
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Boolean... booleans) {

//            String filePath = ConstValue.getUri().toString();
  //          File file = new File(ConstValue.getUri().toString());
           // File originalFile = new File(path);
            String encodedBase64 = null;
            try {
                //FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                FileInputStream inputStream = (FileInputStream) getContentResolver().openInputStream(selectedImage);
                byte[] bytes = new byte[1000000];
                inputStream.read(bytes);
                encodedBase64 = new String(Base64.getEncoder().encode(bytes));
                System.out.println("documento en base 64: " + encodedBase64);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
