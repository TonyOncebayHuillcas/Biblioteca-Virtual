package com.example.bibliotecavirtual.Adapters;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bibliotecavirtual.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentAdapter extends BaseAdapter {

    Context context;
    private ArrayList<HashMap<String, String>> postItems;

    public DocumentAdapter(Context context, ArrayList<HashMap<String, String>> postItems) {
        this.context = context;
        this.postItems = postItems;
    }

    @Override
    public int getCount() {
        return postItems.size();
    }

    @Override
    public Object getItem(int position) {
        return postItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_document, null);
        }
        final HashMap<String, String> map = postItems.get(position);

        TextView name_of_document = (TextView) convertView.findViewById(R.id.tv_name_of_document);
        name_of_document.setText(map.get("name_of_document"));

        TextView author = (TextView) convertView.findViewById(R.id.tv_author);
        author.setText(map.get("author"));

        TextView thema = (TextView) convertView.findViewById(R.id.tv_thema);
        thema.setText(map.get("thema"));

        TextView cant_descargas = (TextView)  convertView.findViewById(R.id.tv_cant_descargas);
        cant_descargas.setText(map.get("cant_descargas"));

        return convertView;
    }
}
