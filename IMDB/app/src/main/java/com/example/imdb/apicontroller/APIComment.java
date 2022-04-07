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
        Call<Integer> call= api.addRating(c);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    i.onRatingSuccess(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public interface CallAPIComment{

        @GET("comment/getAllCommentOfMovie/{id}")
        Call<List<Comment>> getAllCommentOfMovie(@Path("id") int id);

        @POST("comment/addRating")
        Call<Integer> addRating(@Body Comment comment);


    }

}
