package com.example.dynamicplanner;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<String> taskList;
    private Context mContext;
    private String strPicked;
    private Integer type;

    // Counstructor for the Class
    public SceneAdapter(Integer type, ArrayList<String> tasklist, Context context, String strpicked) {
        this.taskList = tasklist;
        this.mContext = context;
        this.strPicked = strpicked;
        this.type = type;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(type == 0){
            return new ContactHolder(layoutInflater.inflate(R.layout.tasklistblack, parent, false));
        }else{
            return new ContactHolder(layoutInflater.inflate(R.layout.tasklist, parent, false));
        }


    }

    @Override
    public int getItemCount() {
        return taskList == null? 0: taskList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NotNull ContactHolder holder, final int position) {
        final String strTask = taskList.get(position);

        if(strPicked != null){
            if(strPicked.equalsIgnoreCase(strTask)){
                holder.rlTaskList.setBackground(ContextCompat.getDrawable(mContext, R.drawable.layouttransparentoorange_bg));
                holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
        // Set the data to the views here
        holder.txtTitle.setText(strTask);

        holder.imgDeleteBin.setOnClickListener(v-> removeAt(position));

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private ImageView imgDeleteBin;
        private TextView txtTitle;
        private RelativeLayout rlTaskList;


        public ContactHolder(View itemView) {
            super(itemView);

            imgDeleteBin = itemView.findViewById(R.id.imgDeleteBin);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            rlTaskList = itemView.findViewById(R.id.rlTaskList);
        }

    }

    public void removeAt(int position) {
        taskList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
    }
}