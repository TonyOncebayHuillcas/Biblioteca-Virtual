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

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(NewDocumentActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context applicationContext = MainActivity.getContextOfApplication();
        if (resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            System.out.println("URI : " + selectedImage);
            System.out.println("URI -path : " + selectedImage.getPath());

            Cursor cursor = applicationContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
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
            File originalFile = new File("/content:/com.mi.android.globalFileexplorer.myprovider/external_files/Download/Informe.pdf");
            String encodedBase64 = null;
            try {
                FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                byte[] bytes = new byte[(int)originalFile.length()];
                fileInputStreamReader.read(bytes);
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
