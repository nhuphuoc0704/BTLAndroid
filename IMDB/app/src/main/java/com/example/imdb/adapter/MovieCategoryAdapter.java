package com.example.imdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.R;
import com.example.imdb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.ViewHolder> {
    List<Movie> data;
    Context context;
    IOnClickItem i;

    public MovieCategoryAdapter(Context context){
        this.context=context;
    }
    public void setIOnClickItem(IOnClickItem i){
        this.i=i;
    }
    public void setData(List<Movie> indata){
        data=indata;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie m= data.get(position);
        holder.tvname.setText(m.getName());
        Picasso.with(context).load(m.getImage()).into(holder.img);
        if(m.getStar()!=0)
            holder.tvStarItemMovie.setText(m.getStar()+"");
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.onClickImage(m);
            }
        });
        holder.tvname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.onClickName(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvname,tvStarItemMovie;
        TextView tvWatchlist;
        ImageView icAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgMovie);
            tvname=itemView.findViewById(R.id.tvNameMovie);
            tvWatchlist=itemView.findViewById(R.id.tvWatchlist);
            icAdd= itemView.findViewById(R.id.imgAddWatchlist);
            tvStarItemMovie= itemView.findViewById(R.id.tvStarItemMovie);
        }
    }

    public interface IOnClickItem{
        void onClickImage(Movie m);
        void onClickName(int position);
    }
}
