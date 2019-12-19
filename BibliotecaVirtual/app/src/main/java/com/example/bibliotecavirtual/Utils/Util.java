package com.example.bibliotecavirtual.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Views.Activitys.LoginActivity;

public class Util {

    public static void logout(final Activity activity, final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_logout);
        TextView head = (TextView) dialog.findViewById(R.id.alert_logout_title);
        head.setText("Easy Note");
        TextView content = (TextView) dialog.findViewById(R.id.alert_logout_content);
        content.setText("Está seguro de cerrar sesión?");

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                SharedPreferences sharedPref = getSharedPreferences("login_preferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("logueado", "inactive");
                editor.apply();
*/
                SqliteClass.getInstance(context).databasehelp.deleteDataBase();

                Intent login=new Intent(context, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dialog.dismiss();


                activity.finish();
            }
        });
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



}