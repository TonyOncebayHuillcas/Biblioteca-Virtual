package com.example.bibliotecavirtual.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    List<DocumentClass> itemList;
    static ArrayList<HashMap<String, String>> itemArray;
    ListView itemListView;
    TextView empty;
    Activity activity;
    Context context;
    int cnt;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        context = getContext();
        itemArray = new ArrayList<HashMap<String,String>>();
        itemListView = (ListView) layout.findViewById(android.R.id.list);
        empty  = (TextView) layout.findViewById(android.R.id.empty);
        //itemList = SqliteClass.getInstance(context).databasehelp.ordersql.getAllItem();
        //getList(itemList);
        // Inflate the layout for this fragment
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
