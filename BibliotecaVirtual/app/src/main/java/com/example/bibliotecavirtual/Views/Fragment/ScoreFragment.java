package com.example.bibliotecavirtual.Views.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;


public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView totaldoc,totaldocDescargados;
    ArrayList<DocumentClass> documentClasses = new ArrayList<DocumentClass>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_score, container, false);
        totaldoc = (TextView) layout.findViewById(R.id.tv_totalarchivos);

        totaldocDescargados = (TextView) layout.findViewById(R.id.tv_count_download);

        documentClasses = SqliteClass.getInstance(getActivity()).databasehelp.documentsql.getContadorUser(SqliteClass.getInstance(getActivity()).databasehelp.usersql.getID());
        int totaldocDescargado=0;
        int totaldoc_=documentClasses.size();
        for (int i=0;i<documentClasses.size();i++){
            totaldocDescargado = documentClasses.get(i).getContador();
        }

        totaldoc.setText(totaldoc_+" archivos subidos");
        totaldocDescargados.setText(""+totaldocDescargado);

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
