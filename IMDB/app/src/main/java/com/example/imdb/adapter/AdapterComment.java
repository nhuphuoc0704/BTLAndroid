package com.example.imdb.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.Comment;
import com.example.imdb.model.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    List<Comment> data;
    Context context;
    IOnClickOption i;
    SharedPreferences sharedPreferences;

    public AdapterComment(Context context){
        this.context=context;
        sharedPreferences= MainActivity.sharedPreferences;
    }

    public void setData(List<Comment> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public List<Comment> getData(){
        return data;
    }
    public void setOnClickOption(IOnClickOption i){
        this.i=i;
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
        User userComment= c.getUser();
        User mUser=getMyUser();

        //Log.e("User id",userComment.getId()+"");
        if( userComment.getAvatar().length()==0){
            holder.img.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
        }
        else
            Picasso.with(context).load(userComment.getAvatar()).into(holder.img);

        holder.tvContent.setText(c.getContent());
        holder.tvTitle.setText(c.getTitle());
        holder.tvStar.setText(c.getStar()+"");
        holder.tvTimeCreate.setText(c.getCreated_time());
        holder.tvNameOfUserComment.setText(userComment.getFullname());
        holder.imgOption.setVisibility(View.GONE);
        if(mUser!=null){
            if(  mUser.getId()==userComment.getId()){
                holder.imgOption.setVisibility(View.VISIBLE);
                holder.imgOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        i.onClickOption(position,view);
                    }
                });
            }

        }



    }

    private User getMyUser() {
        String jsonUser= sharedPreferences.getString("user","");
        Gson gson= new Gson();
                return jsonUser.equals("")? null: gson.fromJson(jsonUser,User.class);

    }

    @Override
    public int getItemCount() {
        return data==null? 0:data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView imgOption;
        TextView tvNameOfUserComment,tvTimeCreate,tvTitle, tvContent,tvStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.imgAvatarComment);
            tvNameOfUserComment= itemView.findViewById(R.id.tvNameOfUserComment);
            tvTimeCreate= itemView.findViewById(R.id.tvTimeCreate);
            tvTitle= itemView.findViewById(R.id.tvTitleComment);
            tvContent= itemView.findViewById(R.id.tvContent);
            tvStar= itemView.findViewById(R.id.tvStarComment);
            imgOption= itemView.findViewById(R.id.imgOption);


        }
    }

    public void updateEditRating(Comment c){
        for (Comment cmt: data
             ) {
            if(c.getId()==cmt.getId()){
                cmt=c;
                break;
            }
        }
        notifyDataSetChanged();
    }


    public interface IOnClickOption{
        public void onClickOption(int position,View v);
    }
}
