package com.example.bibliotecavirtual.Views.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.DB.SqliteClass;
import com.example.bibliotecavirtual.Views.Activitys.DetailDocumentActivity;
import com.example.bibliotecavirtual.Adapters.DocumentAdapter;
import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.Views.Activitys.NewDocumentActivity;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    List<DocumentClass> itemList;
    static ArrayList<HashMap<String, String>> itemArray;
    DocumentAdapter adapter;
    ListView itemListView;
    TextView empty;
    Activity activity;
    Context context;
    FloatingActionButton DocEvent;
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
        itemListView = (ListView) layout.findViewById(R.id.list_documents);
        empty  = (TextView) layout.findViewById(android.R.id.empty);
        itemList = SqliteClass.getInstance(context).databasehelp.documentsql.getAllItem();
        getList(itemList);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ConstValue.setNombre(itemList.get(position).getNombre());
                ConstValue.setCodTema(itemList.get(position).getCodTema());
                ConstValue.setIdDoc(itemList.get(position).getIdDoc());
                ConstValue.setCodUsuario(SqliteClass.getInstance(context).databasehelp.userssql.getNameUser(itemList.get(position).getCodUsuario()));
                ConstValue.setContador(itemList.get(position).getContador());


                Intent intent = new Intent(context, DetailDocumentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        DocEvent = (FloatingActionButton) layout.findViewById(R.id.action_new_event_trip);
        DocEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDocumentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                //finish();
            }
        });
        return layout;
    }

    public void getList(List<DocumentClass> list){
        itemArray = new ArrayList<HashMap<String,String>>();
        itemList = new ArrayList<DocumentClass>();
        itemList = list;
        cnt=0;
        for(int z=0; z < list.size(); z++){
            DocumentClass cc = list.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            //map.put("id", String.valueOf(cc.getId()));
            map.put("name_of_document",cc.getNombre());
            map.put("author",SqliteClass.getInstance(context).databasehelp.userssql.getNameUser(cc.getCodUsuario()));
            map.put("thema",SqliteClass.getInstance(context).databasehelp.temasql.getNameTem(cc.getCodTema()));
            map.put("cant_descargas",String.valueOf(cc.getContador()));

            itemArray.add(map);
        }
        adapter = new DocumentAdapter(activity, itemArray);
        itemListView.setAdapter(adapter);
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
