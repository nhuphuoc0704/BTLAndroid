package com.example.imdb.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.R;
import com.example.imdb.model.Comment;
import com.example.imdb.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    List<Comment> data;
    Context context;

    public AdapterComment(Context context){
        this.context=context;
    }

    public void setData(List<Comment> data){
        this.data=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment c= data.get(position);
        User u= c.getUser();
        Log.e("User id",u.getId()+"");
        if(u.getAvatar()==null || u.getAvatar().length()==0){
            holder.img.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
        }
        else
            Picasso.with(context).load(u.getAvatar()).into(holder.img);

        holder.tvContent.setText(c.getContent());
        holder.tvTitle.setText(c.getTitle());
        holder.tvStar.setText(c.getStar()+"");
        holder.tvTimeCreate.setText(c.getCreated_time());
        holder.tvNameOfUserComment.setText(u.getFullname());

    }

    @Override
    public int getItemCount() {
        return data==null? 0:data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView tvNameOfUserComment,tvTimeCreate,tvTitle, tvContent,tvStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.imgAvatarComment);
            tvNameOfUserComment= itemView.findViewById(R.id.tvNameOfUserComment);
            tvTimeCreate= itemView.findViewById(R.id.tvTimeCreate);
            tvTitle= itemView.findViewById(R.id.tvTitleComment);
            tvContent= itemView.findViewById(R.id.tvContent);
            tvStar= itemView.findViewById(R.id.tvStarComment);

        }
    }
}
