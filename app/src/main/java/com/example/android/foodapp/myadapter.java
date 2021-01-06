package com.example.android.foodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.foodapp.ui.home.shops;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<vHolder> implements Filterable {

    ArrayList<Model> data;
    ArrayList<Model> backup;
    Context context;
    public myadapter(ArrayList<Model> data, Context context) {
        this.data = data;
        this.context = context;
        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter,parent,false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        final Model temp =data.get(position);

        holder.t1.setText(data.get(position).getHeader());
        holder.t2.setText(data.get(position).getDesc());
        holder.img.setImageResource(data.get(position).getImgName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do on click stuff
                Toast.makeText(context, " something is better ", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context, shops.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.img.setOnClickListener((v) -> {
            Intent intent = new Intent(context,homeclick.class);
            intent.putExtra("ImageName",temp.getImgName());
            intent.putExtra("Header",temp.getHeader());
            intent.putExtra("Desc",temp.getDesc());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Model> filterdata = new ArrayList<>();
            if(keyword.toString().isEmpty()){
                filterdata.addAll(backup);
            }
            else {
                for (Model obj:backup){
                    if(obj.getHeader().toString().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filterdata.add(obj);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=filterdata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            data.clear();
            data.addAll((ArrayList<Model>)results.values);
            notifyDataSetChanged();
        }
    };
}
