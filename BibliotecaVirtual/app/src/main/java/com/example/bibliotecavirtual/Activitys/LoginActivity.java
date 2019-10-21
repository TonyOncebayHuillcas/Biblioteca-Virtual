package com.example.bibliotecavirtual.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.bibliotecavirtual.R;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login,sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        login = (Button) findViewById(R.id.button_login);
        sign = (Button) findViewById(R.id.button_sign_up);
    }
}
