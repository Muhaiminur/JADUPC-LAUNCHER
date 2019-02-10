package com.easy.abir.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class App_List_Adapter extends RecyclerView.Adapter<App_List_Adapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView app_name;
        public ImageView app_icon;
        public CardView app_card;

        public MyViewHolder(View view) {
            super(view);
            app_name = view.findViewById(R.id.app_name);
            app_icon = view.findViewById(R.id.app_icon);
            app_card = view.findViewById(R.id.app_card);
        }
    }

    List<String> stringList;
    Context context1;
    App_List_Adapter(Context context, List<String> list){
        stringList=list;
        context1 = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view2 = LayoutInflater.from(context1).inflate(R.layout.app_list_row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view2);
        return viewHolder;*/
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);


        final String ApplicationPackageName = (String) stringList.get(position);
        String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        holder.app_name.setText(ApplicationLabelName);

        //viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);

        holder.app_icon.setImageDrawable(drawable);

        //Adding click listener on CardView to open clicked application directly from here .
        holder.app_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){

                    context1.startActivity(intent);

                }
                else {

                    Toast.makeText(context1,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void filterList(ArrayList<String> filterdNames) {
        this.stringList = filterdNames;
        notifyDataSetChanged();
    }
}