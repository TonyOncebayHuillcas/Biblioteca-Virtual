package com.example.bibliotecavirtual.Activitys;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bibliotecavirtual.Adapters.MenuAdapter;
import com.example.bibliotecavirtual.Config.ConstValue;
import com.example.bibliotecavirtual.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    Context context;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDeawerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    ArrayList<HashMap<String, Integer>> menuArray;
    ListView mListView;
    MenuAdapter mAdapter;

    public SharedPreferences settings;
    //public ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
        //cd=new ConnectionDetector(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflator.inflate(R.layout.action_bar_title, null);
        //actionBar.setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDeawerView = (LinearLayout)findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("User");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(R.drawable.background_01);

        menuArray = new ArrayList<HashMap<String,Integer>>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put("option", R.string.pagina_principal);
        map.put("image", R.drawable.ic_person);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.filtro_por);
        map.put("image", R.drawable.ic_person);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.puntaje);
        map.put("image", R.drawable.ic_person);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.mi_perfil);
        map.put("image", R.drawable.ic_person);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.cerrar_sesion);
        map.put("image", R.drawable.ic_person);
        menuArray.add(map);

        mListView = (ListView)findViewById(R.id.list_navigability);
        mAdapter = new MenuAdapter(getApplicationContext(), menuArray);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                set_fragment_page(arg2);

            }
        });

        if(getIntent().hasExtra("fragment_position")){
            set_fragment_page(getIntent().getExtras().getInt("fragment_position"));
        }else {
            set_fragment_page(0);
        }

    }

    public void set_fragment_page(int position){
        Fragment fragment = null;
        Intent intent = null;

        Bundle args;
        switch (position) {
            case 0:
                //fragment = new HomeFragment();
                //7args = new Bundle();
                ///fragment.setArguments(args);
                break;
            case 1:
                //fragment = new ProfileFragment();
                break;
            case 2:
                //intent = new Intent(MainActivity.this, ViewcartActivity.class);
                break;
            case 3:
                //fragment = new QueryFragment();
                break;

            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        if (fragment!=null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            if (position==0) {
                fragmentManager.beginTransaction()
                        .replace(R.id.sample_content_fragment, fragment)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.sample_content_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
            mDrawerLayout.closeDrawer(mDeawerView);
        }
    }
}
