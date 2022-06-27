package com.example.imdb.presenter;

import android.util.Log;

import com.example.imdb.apicontroller.APICategory;
import com.example.imdb.apicontroller.APIController;
import com.example.imdb.apicontroller.APIUser;
import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;
import com.example.imdb.model.MovieWatchList;

import java.util.List;

public class PresenterHomeFragment {
    private IOnHomeFragment i;
    private APICategory apiCategory;
    public PresenterHomeFragment(IOnHomeFragment i){
        this.i=i;
        apiCategory=new APICategory(i);
    }

    public void onGetAllCategory(){

        apiCategory.getAllCategory();
    }

    public void addMovieWatchList(MovieWatchList m){
        apiCategory.addMovieWatchList(m);
    }

    public void removeMovieWatchList(int id_movie, int id_user){
        apiCategory.removeMovieWatchList(id_movie, id_user);
    }

    public interface IOnHomeFragment{
        void onGetData(List<Category> list);

        void addMovieWatchList(int id);

        void removeMovieWatchListResult(int result);
        
    }
}
