package com.example.imdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterWatchList extends RecyclerView.Adapter<AdapterWatchList.ViewHolder> {
    MainActivity activity;
    Context context;
    List<Movie> data;
    IOnClickItemWatchList iOnClickItemWatchList;

    public AdapterWatchList(MainActivity mainActivity,Context context){
        this.activity= mainActivity;
        this.context= context;
    }
    public void setData(List<Movie> list){
        data=list;
        notifyDataSetChanged();
    }

    public void setiOnClickItemWatchList(IOnClickItemWatchList iOnClickItemWatchList){
        this.iOnClickItemWatchList= iOnClickItemWatchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_list_movie,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie m = data.get(position);
        holder.tvName.setText(m.getName());
        holder.tvDirectors.setText(m.getDirector());
        holder.tvStar.setText(m.getStar()+"");
        holder.tvStars.setText(m.getStars());
        Picasso.with(context).load(m.getImage()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemWatchList.onClickImage(m);
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemWatchList.onClickNameMovie(m);
            }
        });

        holder.imgBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemWatchList.onClickRemove(m);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data==null? 0: data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDirectors, tvStars, tvStar;
        ImageView img;
        ImageButton imgBtnRemove;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvName= v.findViewById(R.id.tvNameMovieWatchList);
            tvDirectors= v.findViewById(R.id.tvDirectorWatchList);
            tvStars= v.findViewById(R.id.tvStarsWatchList);
            tvStar= v.findViewById(R.id.tvStarWatchList);
            img= v.findViewById(R.id.imgItemWatchList);
            imgBtnRemove= v.findViewById(R.id.imgBtnRemove);


        }
    }
    public interface IOnClickItemWatchList{
        public void onClickNameMovie(Movie m);
        public void onClickImage(Movie m);
        public void onClickRemove(Movie m);
    }
}
