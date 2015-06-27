package com.example.nischal.materialdesign1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nischal on 21-06-2015.
 */
public class recycleradapter extends RecyclerView.Adapter<recycleradapter.myViewHolder> {

    private  LayoutInflater inflator;
    List<information> data= Collections.emptyList();

    public recycleradapter(Context context,List<information> data){
        inflator= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflator.inflate(R.layout.custum_recycler_layout,parent,false);
        myViewHolder holder=new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(recycleradapter.myViewHolder holder, int position) {
        information current=data.get(position);
        holder.icon.setImageResource(current.iconid);
        holder.title.setText(current.title);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;

        public myViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.recycler_text);
            icon= (ImageView) itemView.findViewById(R.id.recycler_image_icon);
        }
    }
}
