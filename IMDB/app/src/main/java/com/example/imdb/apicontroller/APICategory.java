package com.example.imdb.apicontroller;

import com.example.imdb.model.Category;
import com.example.imdb.presenter.PresenterHomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

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

            }
        });
    }
    public interface CallAPICategory{
        @GET("category/getAll/")
        Call<List<Category>> getAllCategory();

    }
}
