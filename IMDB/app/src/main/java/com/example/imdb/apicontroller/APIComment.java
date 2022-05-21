package com.example.imdb.apicontroller;

import android.util.Log;

import com.example.imdb.model.Comment;
import com.example.imdb.presenter.PresenterComment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class APIComment extends APIController {
    private PresenterComment.IOnComment i;
    private CallAPIComment api;

    public APIComment(PresenterComment.IOnComment i) {
        super();
        this.i=i;
        api=retrofit.create(CallAPIComment.class);
    }
    public void getAllCommentOfMovie(int id){
        Call<List<Comment>> call= api.getAllCommentOfMovie(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()){
                    Log.e("Get all comment success","Get all comment success");
                    i.getAllCommentOfMovie(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    public void addRating(Comment c){
        Call<Comment> call= api.addRating(c);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    i.onRatingSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    public void editRating(Comment c,float oldStar){
        Call<Comment> call= api.editRating(c,oldStar);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    i.editRating(response.body());
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

    }
    public interface CallAPIComment{

        @GET("comment/getAllCommentOfMovie/{id}")
        Call<List<Comment>> getAllCommentOfMovie(@Path("id") int id);

        @POST("comment/addRating")
        Call<Comment> addRating(@Body Comment comment);

        @PUT("comment/editRating/{oldStar}")
        Call<Comment> editRating(@Body Comment comment,@Path("oldStar") float oldStar);


    }

}
