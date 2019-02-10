package com.easy.abir.launcher;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> app_list_name=new ArrayList<>();
    Context context;

    private RecyclerView recyclerView;
    private App_List_Adapter mAdapter;

    AppWidgetManager mAppWidgetManager;
    AppWidgetHost mAppWidgetHost;
    ViewGroup mainlayout;
    int count =0;


    EditText Search_App;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            context=MainActivity.this;
            app_list_name.addAll(new ApkInfoExtractor(context).GetAllInstalledApkInfo());
            Log.d("list Size main",app_list_name.size()+"");
            //create_Fragment();
            app_list_recycler();
            //finding_two_widget();
            Search_Result();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void app_list_recycler(){
        try {
            recyclerView =findViewById(R.id.app_list_recycler_main);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
            // adding inbuilt divider line
            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //mAdapter= new App_List_Adapter(context, new ApkInfoExtractor(context).GetAllInstalledApkInfo());
            mAdapter= new App_List_Adapter(context,app_list_name);
            //Log.d("list",new ApkInfoExtractor(context).GetAllInstalledApkInfo().size()+"");
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void finding_two_widget(){
        try {
            mAppWidgetHost = new AppWidgetHost(this, R.string.appWidgetHost_id);
            mAppWidgetManager = AppWidgetManager.getInstance(this);
            mainlayout = (ViewGroup) findViewById(R.id.main_layout);
            for (int c=0;c<app_list_name.size();c++){
                if (createWidget(mainlayout,app_list_name.get(c))&&count>2){
                    break;
                }else {
                    Log.d("name",app_list_name.get(c)+c);
                    count++;
                }
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public boolean createWidget(View view, String packageName) {
        try {
            // Get the list of installed widgets
            AppWidgetProviderInfo newAppWidgetProviderInfo = null;
            List<AppWidgetProviderInfo> appWidgetInfos;
            appWidgetInfos = mAppWidgetManager.getInstalledProviders();
            boolean widgetIsFound = false;
            for(int j = 0; j < appWidgetInfos.size(); j++)
            {
                if (appWidgetInfos.get(j).provider.getPackageName().equals(packageName) /*|| appWidgetInfos.get(j).provider.getClassName().equals(className)||true*/)
                {
                    // Get the full info of the required widget
                    newAppWidgetProviderInfo = appWidgetInfos.get(j);
                    widgetIsFound = true;
                    break;
                }
            }

            if (!widgetIsFound) {
                Log.d("not working","three");
                return false;
            } else {
                // Create Widget
                int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
                AppWidgetHostView hostView = mAppWidgetHost.createView(getApplicationContext(), appWidgetId, newAppWidgetProviderInfo);
                hostView.setAppWidget(appWidgetId, newAppWidgetProviderInfo);

                // Add it to your layout
                LinearLayout widgetLayout = view.findViewById(R.id.launcher_layout);
                widgetLayout.addView(hostView);

                // And bind widget IDs to make them actually work
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    boolean allowed = mAppWidgetManager.bindAppWidgetIdIfAllowed(appWidgetId, newAppWidgetProviderInfo.provider);

                    if (!allowed) {
                        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, newAppWidgetProviderInfo.provider);
                        final int REQUEST_BIND_WIDGET = 1987;
                        startActivityForResult(intent, REQUEST_BIND_WIDGET);
                    }else {
                        Log.d("not working","two");
                    }
                }else {
                    Log.d("not working","one");
                }

                return true;
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return true;
    }

    public  void Search_Result(){
        try {
            Search_App= findViewById(R.id.search_app);
            Search_App.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //after the change calling the method and passing the search input
                    filter(editable.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            ArrayList<String> filterdNames = new ArrayList<>();

            //looping through existing elements
            for (String s : app_list_name) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }

            //calling a method of the adapter class and passing the filtered list
            mAdapter.filterList(filterdNames);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
