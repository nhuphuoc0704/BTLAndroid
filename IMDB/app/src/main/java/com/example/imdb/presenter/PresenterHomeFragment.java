package com.example.imdb.presenter;

import android.util.Log;

import com.example.imdb.apicontroller.APICategory;
import com.example.imdb.apicontroller.APIController;
import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;

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

    public interface IOnHomeFragment{
        void onGetData(List<Category> list);
        
    }
}
