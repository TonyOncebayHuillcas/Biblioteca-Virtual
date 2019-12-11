package com.example.bibliotecavirtual.Views.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import com.example.bibliotecavirtual.Views.Activitys.LoginActivity;
import com.example.bibliotecavirtual.Views.Activitys.MainActivity;
import com.example.bibliotecavirtual.Views.Activitys.NewDocumentActivity;
import com.example.bibliotecavirtual.R;
import com.example.bibliotecavirtual.Views.Activitys.RegisterActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.gauriinfotech.commons.Commons;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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
    int VALOR_RETORNO = 1;

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

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);


                /*

                */
                //finish();
            }
        });
        return layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filepath=null;
        String filename;
        Context applicationContext = MainActivity.getContextOfApplication();
        if (resultCode == MainActivity.RESULT_OK) {

            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                filepath =path;
                System.out.println("direccón del path: "+ filepath);
                String displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = applicationContext.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            //String txtFilename.setText(displayName);
                            filename = displayName;

                            System.out.println("displayname: "+ displayName );
                            System.out.println("filename: "+ filename );
                            System.out.println("direccón: "+ Environment.getExternalStorageDirectory() );
                            //Environment.getExternalStorageDirectory()

                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                    //txtFilename.setText(displayName);
                    filename = displayName;
                    System.out.println("displayname: "+ displayName );
                    System.out.println("filename: "+ filename );
                    System.out.println("direccón: "+ Environment.getExternalStorageDirectory() );


                }
            }

            Intent intent = new Intent(context, NewDocumentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPDFPath(Uri uri) {
        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("/storage/emulated/0/Download/"), Long.valueOf(id));
        String[] projection = { MediaStore.Images.Media.DATA };
        Context applicationContext = MainActivity.getContextOfApplication();
        //applicationContext.getContentResolver();
        Cursor cursor = applicationContext.getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

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
