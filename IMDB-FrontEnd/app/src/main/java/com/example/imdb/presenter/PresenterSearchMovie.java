package com.example.imdb.presenter;

import com.example.imdb.apicontroller.APISearchable;
import com.example.imdb.model.Movie;

import java.util.List;

public class PresenterSearchMovie {
    private IOnSearchable i;
    private APISearchable api;

    public PresenterSearchMovie(IOnSearchable i){
        this.i=i;
        api=new APISearchable(i);
    }

    public void searchable(String str){
        api.searchable(str);
    }

    public interface IOnSearchable{
        public void searchable(List<Movie> list);
    }
}
