package com.example.bibliotecavirtual.Views.Activitys;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Utils.Protocol;

public class DetailDocumentActivity extends AppCompatActivity {
    ActionBar actionBar;
    TextView title,autor,universidad,descargas;
    Button download;
    Protocol protocol;

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

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Salida" + protocol.getJson(ConstValue.URL_GET_DOCUMENT_ID+"5dd8bc662fb78f0004ad7624"));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(DetailDocumentActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
