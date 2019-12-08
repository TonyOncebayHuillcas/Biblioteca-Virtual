package com.example.bibliotecavirtual.Views.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Models.UserClass;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    TextView name,email, university;
    ArrayList<UserClass> userClasse;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);

        userClasse= new ArrayList<UserClass>();
        context = getContext();
        userClasse= SqliteClass.getInstance(context).databasehelp.usersql.getUserData();
        name = (TextView) layout.findViewById(R.id.tv_name_profile);
        name.setText(userClasse.get(0).getUserName());

        email = (TextView) layout.findViewById(R.id.tv_email_profile);
        email.setText(userClasse.get(0).getCorreo());

        university =(TextView) layout.findViewById(R.id.tv_university_profile);
        university.setText("UNSA");

        return layout;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
