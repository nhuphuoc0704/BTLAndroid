package com.example.imdb.apicontroller;

import android.util.Log;

import com.example.imdb.model.MovieWatchList;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterUser;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class APIUser extends APIController {
    private PresenterUser.IOnUser i;
    public APIUser(PresenterUser.IOnUser i){
        super();
        this.i=i;
    }
    public void checkLogin(String username,String password){
        CallAPIUser api= retrofit.create(CallAPIUser.class);
        Call<User> call= api.checkLogin(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User u=response.body();
                    i.checkLogin(u);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(APIUser.class.getName(),t.getMessage());
            }
        });
    }


    public void registry(User u){
        CallAPIUser api= retrofit.create(CallAPIUser.class);
        Call<User> call= api.registry(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    i.registry(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void loginByGmail(User u){
        CallAPIUser api= retrofit.create(CallAPIUser.class);
        Call<User> call= api.loginByGmail(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User u= response.body();
                    i.loginByGmail(u);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }



    public interface CallAPIUser{
        @GET("user/checklogin/{username}/{password}")
        Call<User> checkLogin(@Path("username") String username,@Path("password") String password);

//        @GET("user/getuser/")
//        Call<User> getUser(@Body User u);

        @POST("user/registry/")
        Call<User> registry(@Body User u);

        @POST("user/loginbygmail/")
        Call<User> loginByGmail(@Body User u);




    }
}
