package com.example.imdb.apicontroller;

import android.util.Log;

import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;
import com.example.imdb.presenter.PresenterHomeFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class APIController  {
    public static Retrofit retrofit;
    public APIController( ){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit=  new Retrofit.Builder()
                    .baseUrl("http://192.168.100.7:8080/APIRestful/rest/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

    }

}




