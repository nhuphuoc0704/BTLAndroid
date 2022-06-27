package com.example.imdb.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.example.imdb.LoginActivity;
import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.Movie;
import com.example.imdb.model.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.ViewHolder> {
    List<Movie> data;

    Context context;
    IOnClickItem i;
    AnimatedVectorDrawable animated;
    boolean isCheck;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    User user;
    MainActivity mainActivity;



    public MovieCategoryAdapter(Context context,MainActivity mainActivity){
        this.context=context;
        sharedPreferences= MainActivity.sharedPreferences;
        editor=MainActivity.editor;
        user=(new Gson()).fromJson(sharedPreferences.getString("user",""),User.class);
        this.mainActivity= mainActivity;

    }
    public void setIOnClickItem(IOnClickItem i){
        this.i=i;
    }
    public void setData(List<Movie> inData){
        data=inData;

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
        isCheck=false;
        Movie m= data.get(position);
        holder.tvName.setText(m.getName());
        Picasso.with(context).load(m.getImage()).into(holder.img);
        if(m.getStar()!=0)
            holder.tvStarItemMovie.setText(m.getStar()+"");
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.onClickImage(m);
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.onClickName(position);
            }
        });

        if(user!=null){
            for (Movie mv :user.getUserWatchList()
            ) {
               if(mv.equals(m)){
                   isCheck=true;
                   break;
               }
            }

        }

        if(isCheck){
            holder.icAdd.setImageResource(R.drawable.avd_anim_check_to_add);
        }
        else{
            holder.icAdd.setImageResource(R.drawable.avd_anim_add_to_check);
        }

        holder.tvWatchlist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if(user!=null){
                    if(isCheck){
                        i.onRemoveMovieWatchList(m.getId(),user.getId());
                        holder.icAdd.setImageResource(R.drawable.avd_anim_check_to_add);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            user.getUserWatchList().removeIf(m1 -> m1.getId()==m.getId());
                        }
                    }
                    else{
                        i.onClickAddWatchList(m);
                        holder.icAdd.setImageResource(R.drawable.avd_anim_add_to_check);
                        user.getUserWatchList().add(m);

                    }
                    updateWatchList();
                    animated= (AnimatedVectorDrawable) holder.icAdd.getDrawable();
                    animated.start();
                    isCheck=!isCheck;
                }
                else {
                    showDialogLoginRequired();
                }

            }
        });
    }

    private void updateWatchList() {
        Gson gson= new Gson();
        Log.d("update watchlist user",user.getUserWatchList().size()+"");
        String jsonUser= gson.toJson(user);
        editor.putString("user",jsonUser);
        editor.apply();

    }

    private void showDialogLoginRequired() {
        AlertDialog.Builder buidler= new AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage("You must to login first")
                .setTitle("Login required")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Login now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mainActivity.gotoLoginActivity();

                    }
                }).setIcon(R.drawable.ic_baseline_star_24);

        AlertDialog dialog= buidler.create();
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName,tvStarItemMovie;
        TextView tvWatchlist;
        ImageView icAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgMovie);
            tvName=itemView.findViewById(R.id.tvNameMovie);
            tvWatchlist=itemView.findViewById(R.id.tvWatchlist);
            icAdd= itemView.findViewById(R.id.imgAddWatchlist);
            tvStarItemMovie= itemView.findViewById(R.id.tvStarItemMovie);
        }
    }

    public interface IOnClickItem{
        void onClickImage(Movie m);
        void onClickName(int position);
        void onClickAddWatchList(Movie m);
        void onRemoveMovieWatchList(int id_movie,int id_user);
    }
}
