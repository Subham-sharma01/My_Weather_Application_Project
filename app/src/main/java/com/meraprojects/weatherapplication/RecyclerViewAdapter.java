package com.meraprojects.weatherapplication;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<WeatherRVModel> obj1;

    public RecyclerViewAdapter(Context context,ArrayList<WeatherRVModel> obj1){
        this.context=context;
        this.obj1=obj1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recyclervieewitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherRVModel model=obj1.get(position);
        holder.temp.setText(model.getTemperature()+"c");
        //for fetching image from url by picasso dependecypicasso;
        Picasso.get().load("http:".concat(model.getIcon())).into(holder.condition);
        holder.wind.setText(model.getWindspeed()+"km/h");
        SimpleDateFormat input=new SimpleDateFormat("yyyy-mm-dd hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("hh:mm aa");
        try {
            Date t=input.parse(model.getTime());
            holder.time.setText(output.format(t));

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return obj1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wind,temp,time;
        private ImageView condition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind=itemView.findViewById(R.id.windspeed);
            temp=itemView.findViewById(R.id.temperature);
            time=itemView.findViewById(R.id.time);
            condition=itemView.findViewById(R.id.condition);
        }
    }
}
