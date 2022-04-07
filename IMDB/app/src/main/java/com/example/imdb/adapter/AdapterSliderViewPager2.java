package com.example.imdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.R;
import com.example.imdb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSliderViewPager2 extends RecyclerView.Adapter<AdapterSliderViewPager2.ViewHolder> {
    private final Context context;
    private List<Movie> data;

    public AdapterSliderViewPager2(Context context){
        this.context=context;
    }

    public void setData(List<Movie> data){
        this.data=data;
        notifyDataSetChanged();
    }
    public List<Movie> getData(){
        return data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_slider_home_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie m= data.get(position);
        Picasso.with(context).load(m.getImage()).into(holder.photoSlider);

    }

    @Override
    public int getItemCount() {
        return data==null ? 0:data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoSlider;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoSlider= itemView.findViewById(R.id.photoSlider);
        }
    }
}
