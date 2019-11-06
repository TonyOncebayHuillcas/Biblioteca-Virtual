package com.example.bibliotecavirtual.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    List<DocumentClass> itemList;
    static ArrayList<HashMap<String, String>> itemArray;
    ListView itemListView;
    TextView empty;
    Activity activity;
    Context context;
    int cnt;

    public HomeFragment(){}
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        //if(itemList.size()>0){empty.setVisibility(layout.GONE);} else {empty.setVisibility(layout.VISIBLE);}
        return layout;
    }

    public void getList(List<DocumentClass> list){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        List<DocumentClass> filteredValues = new ArrayList<DocumentClass>(itemList);
        for (DocumentClass value : itemList) {
            if (!value.getNombre().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }
        getList(filteredValues);
        return false;
    }

    public void resetSearch() {
        //itemList = SqliteClass.getInstance(context).databasehelp.ordersql.getAllItem();
        //getList(itemList);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}
