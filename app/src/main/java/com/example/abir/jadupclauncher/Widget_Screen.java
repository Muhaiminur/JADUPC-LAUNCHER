package com.example.abir.jadupclauncher;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Widget_Screen extends Fragment {

    List<String> app_list_name=new ArrayList<>();
    View view;
    Context context;

    public Widget_Screen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_widget__screen, container, false);

        app_list_name=getArguments().getStringArrayList("app");
        Log.d("list Size widg",app_list_name.size()+"");
        context=getActivity();

        return view;
    }

    public void create_widget(){

    }

}
