package com.example.imdb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.fragment.FragmentHome;
import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {
    List<Category> data;
    Context context;
    MainActivity mainActivity;
    FragmentHome fragmentHome;
    MovieCategoryAdapter.IOnClickItem iOnClickItem;

    public  CategoryAdapter( Context context,MainActivity mainActivity,FragmentHome fragmentHome){
        this.context=context;
        this.mainActivity=mainActivity;
        this.fragmentHome= fragmentHome;

    }
    public void setData(List<Category> inData){
        data=inData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Category c= data.get(position);
            holder.tvTitle.setText(c.getTitle());
            MovieCategoryAdapter movieCategoryAdapter= new MovieCategoryAdapter(context,mainActivity);
            movieCategoryAdapter.setData(c.getMovies());
            LinearLayoutManager manager= new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(movieCategoryAdapter);

            movieCategoryAdapter.setIOnClickItem(new MovieCategoryAdapter.IOnClickItem() {
                @Override
                public void onClickImage(Movie m) {
                        mainActivity.gotoFragmentDetails(m);
                }

                @Override
                public void onClickName(int position) {

                }

                @Override
                public void onClickAddWatchList(Movie m) {
                    Log.e("Add to watch list","add to watch list");
                    fragmentHome.addMovieWatchList(m);
                }

                @Override
                public void onRemoveMovieWatchList(int id_movie, int id_user) {

                    fragmentHome.removeMovieWatchList(id_movie,id_user);
                }
            });

    }

    @Override
    public int getItemCount() {
        return data==null? 0: data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitleCategory);
            recyclerView=itemView.findViewById(R.id.rvItemCategory);
        }
    }
}
