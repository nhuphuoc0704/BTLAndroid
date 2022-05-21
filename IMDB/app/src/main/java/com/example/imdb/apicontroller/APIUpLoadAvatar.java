package com.example.imdb.apicontroller;

import android.util.Log;

import com.example.imdb.model.DataImage;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterUpLoadAvatar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class APIUpLoadAvatar extends  APIController{
    private PresenterUpLoadAvatar.IOnUpLoadAvatar i;
    public APIUpLoadAvatar(PresenterUpLoadAvatar.IOnUpLoadAvatar i){
        super();
        this.i=i;
    }
    public void uploadAvatar(int id_user, String uriPath){
        RequestBody id= RequestBody.create(MediaType.parse("multipart/form-data"),id_user+"");

        File file= new File(uriPath);
        RequestBody uriBody= RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part mPart= MultipartBody.Part.createFormData("avt",file.getName(),uriBody);

        CallAPIUpLoadAvatar.api.uploadAvatar(id,id,mPart).enqueue(new Callback<DataImage>() {
            @Override
            public void onResponse(Call<DataImage> call, Response<DataImage> response) {

                DataImage data= response.body();
                    i.upLoadSuccess(data.getAvt());
                    Log.e(APIUpLoadAvatar.class.getName(),data.getAvt());

            }

            @Override
            public void onFailure(Call<DataImage> call, Throwable t) {

            }
        });

    }
    public void updateAvatar(User user){
            CallAPIUpdateAvatar api= retrofit.create(CallAPIUpdateAvatar.class);
            Call<Integer> call= api.updateAvatar(user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    int data= response.body();
                    i.updateAvatar(data);
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
    }

    public interface CallAPIUpLoadAvatar{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        CallAPIUpLoadAvatar api =  new Retrofit.Builder()
                .baseUrl("https://accountservermanagement.herokuapp.com/api/accounts/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(CallAPIUpLoadAvatar.class);


        @Multipart
        @POST(".")
        Call<DataImage> uploadAvatar(@Part("username") RequestBody username,
                                     @Part("password") RequestBody password,
                                     @Part MultipartBody.Part  avt);
    }

    public interface CallAPIUpdateAvatar{
        @POST("user/updateAvatar")
        Call<Integer> updateAvatar(@Body User user);
    }
}

