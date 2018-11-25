package com.example.abir.jadupclauncher;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class App_List extends Fragment {


    List<String>app_list_name=new ArrayList<>();
    private RecyclerView recyclerView;
    private App_List_Adapter mAdapter;
    View view;
    Context context;

    public App_List() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_app__list, container, false);

        app_list_name=getArguments().getStringArrayList("app");
        Log.d("list Size list",app_list_name.size()+"");
        context=getActivity();

        recyclerView =view.findViewById(R.id.app_list_recycler);
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
        return view;
    }

}
