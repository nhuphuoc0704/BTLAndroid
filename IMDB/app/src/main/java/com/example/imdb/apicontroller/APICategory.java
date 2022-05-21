package com.example.imdb.apicontroller;

import android.util.Log;

import com.example.imdb.model.Category;
import com.example.imdb.model.MovieWatchList;
import com.example.imdb.presenter.PresenterHomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class APICategory extends APIController{
    private PresenterHomeFragment.IOnHomeFragment iOnHomeFragment;
    public APICategory(PresenterHomeFragment.IOnHomeFragment iOnHomeFragment){
        super();
        this.iOnHomeFragment=iOnHomeFragment;
    }
    public void getAllCategory(){
        CallAPICategory apiCategory= retrofit.create(CallAPICategory.class);
        Call<List<Category>> call= apiCategory.getAllCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> rs= response.body();
                    iOnHomeFragment.onGetData(rs);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                    Log.e("Call api get all catego",t.toString());
            }
        });
    }

    public void addMovieWatchList(MovieWatchList m){
        CallAPICategory api= retrofit.create(CallAPICategory.class);
        Call<Integer> call= api.addMovieToWatchList(m);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                        iOnHomeFragment.addMovieWatchList(response.body());
                    }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    public void removeMovieWatchList(int id_movie, int id_user){
        CallAPICategory api= retrofit.create(CallAPICategory.class);
        Call<Integer> call= api.removeMovieWatchList(id_movie,id_user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    iOnHomeFragment.removeMovieWatchListResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
            }
        });

    }

    public interface CallAPICategory{
        @GET("category/getAll/")
        Call<List<Category>> getAllCategory();

        @POST("category/add_movie_to_watchlist/")
        Call<Integer> addMovieToWatchList(@Body MovieWatchList m);

        @GET("category/remove_movie_watchlist/{id_movie}/{id_user}/")
        Call<Integer> removeMovieWatchList(@Path("id_movie") int id_movie,@Path("id_user") int id_user);

    }
}
