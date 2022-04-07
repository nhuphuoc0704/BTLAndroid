package com.example.imdb.apicontroller;

import com.example.imdb.model.Movie;
import com.example.imdb.presenter.PresenterSearchMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APISearchable extends APIController{
    private PresenterSearchMovie.IOnSearchable i;
    public APISearchable(PresenterSearchMovie.IOnSearchable i){
        super();
        this.i=i;
    }
    public void searchable(String s){
        CallAPISearchable api= retrofit.create(CallAPISearchable.class);
        Call<List<Movie>> call= api.searchMovie(s);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(response.isSuccessful()){
                    i.searchable(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });

    }
    public interface CallAPISearchable{
        @GET("movie/search/{key}")
        Call<List<Movie>> searchMovie(@Path("key") String key);
    }
}
