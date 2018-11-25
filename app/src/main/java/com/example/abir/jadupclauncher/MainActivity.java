package com.example.abir.jadupclauncher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> app_list_name=new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        app_list_name.addAll(new ApkInfoExtractor(context).GetAllInstalledApkInfo());
        Log.d("list Size main",app_list_name.size()+"");
        create_Fragment();
    }
    public void create_Fragment(){
        Bundle app_bundle = new Bundle();
        app_bundle.putStringArrayList("app",app_list_name);
        Fragment app_list_fragment = new App_List();
        app_list_fragment.setArguments(app_bundle);
        Fragment widget_fragment = new Widget_Screen();
        widget_fragment.setArguments(app_bundle);

        FragmentManager fm =getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.app_list_area, app_list_fragment, "app_list_fragment").commit();
        fm.beginTransaction().replace(R.id.widget_area, widget_fragment, "widget_fragment").commit();
    }
}
