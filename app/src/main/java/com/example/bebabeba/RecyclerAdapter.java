package com.example.bebabeba;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Samuel Mathai on 4/23/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Contact> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Contact> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Driver_ID.setText(arrayList.get(position).getDriver_id());
        holder.Latitude.setText(arrayList.get(position).getLatitude());
        holder.Longitude.setText(arrayList.get(position).getLongitude());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Driver_ID, Latitude, Longitude;

        public MyViewHolder(View itemView) {
            super(itemView);
            Driver_ID = (TextView)itemView.findViewById(R.id.driver_id);
            Latitude = (TextView)itemView.findViewById(R.id.latitude);
            Longitude = (TextView)itemView.findViewById(R.id.longitude);
        }
    }
}
