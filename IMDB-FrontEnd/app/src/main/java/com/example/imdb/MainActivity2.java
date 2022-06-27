//package com.example.imdb;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Dialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.imdb.apicontroller.APIController;
//import com.example.imdb.model.Category;
//import com.example.imdb.model.Course;
//import com.example.imdb.model.Movie;
//import com.example.imdb.model.User;
//import com.example.imdb.presenter.PresenterHomeFragment;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Path;
//
//public class MainActivity2 extends AppCompatActivity implements PresenterHomeFragment.IOnHomeFragment {
//    List<Movie> mList= new ArrayList<>();
//    TextView tv;
//    public static final String MY_ACTION="dfsf";
//    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(MY_ACTION.equals(intent.getAction())){
//                String data= intent.getStringExtra("data");
//                tv.setText(data);
//            }
//
//        }
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        tv= findViewById(R.id.tvCount);
//        Button btnPost= findViewById(R.id.btnPost);
//        btnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //clickSendBroadcast();
//
//            }
//        });
//
////        Gson gson = new GsonBuilder()
////                .setLenient()
////                .create();
////        Retrofit retrofit = new Retrofit.Builder()
////                .baseUrl(djangoapi.baseURL)
////                .addConverterFactory(GsonConverterFactory.create(gson))
////                .build();
////        djangoapi api= retrofit.create(djangoapi.class);
////        Button btnPost=findViewById(R.id.btnPost);
////        Call<List<User>> call=api.getUser();
////        call.enqueue(new Callback<List<User>>() {
////            @Override
////            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
////                if(response.isSuccessful()){
////                    tv.setText(response.body().size()+"");
////                }
////
////            }
////
////            @Override
////            public void onFailure(Call<List<User>> call, Throwable t) {
////
////            }
////        });
////        btnPost.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Call<User> call1= api.createUser(new User("11","11","111"));
////                call1.enqueue(new Callback<User>() {
////                    @Override
////                    public void onResponse(Call<User> call, Response<User> response) {
////
////                            tv.setText(response.code()+"");
////
////
////                    }
////
////                    @Override
////                    public void onFailure(Call<User> call, Throwable t) {
////
////                    }
////                });
////            }
////        });
//
//    }
//
//    private void clickSendBroadcast() {
//        Intent i= new Intent(MY_ACTION);
//        i.putExtra("data","hello");
//        sendBroadcast(i);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerReceiver(broadcastReceiver,new IntentFilter(MY_ACTION));
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(broadcastReceiver);
//    }
//
//    @Override
//    public void onGetData(List<Category> list) {
//
//    }
//
//    @Override
//    public void addMovieWatchList(int id) {
//
//    }
//
//    @Override
//    public void removeMovieWatchList(int results) {
//
//    }
//
//
//    public void onGetMovie(List<Movie> list) {
//        if(list.size()==28){
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(djangoapi.baseURL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//            djangoapi api= retrofit.create(djangoapi.class);
//            Call<List<Movie>> call= api.insertMovie(list);
//            call.enqueue(new Callback<List<Movie>>() {
//                @Override
//                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
//
//                    Log.i("insert movie","Success"+list.size());
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Movie>> call, Throwable t) {
//
//                }
//            });
//        }
//
//
//    }
//
//    public interface djangoapi{
//        String baseURL="http://192.168.1.5:8080/APIRestful/rest/";
//        @GET("getAll/")
//        Call<List<User>> getUser();
//
//        @POST("create/")
//        Call<User> createUser(@Body User user);
//
//        @POST("movie/insertmovie/")
//        Call<List<Movie>> insertMovie(@Body List<Movie> list);
//
//        @GET("{title}")
//        Call<List<Movie>> getAllMovie(@Path("title") String title);
//    }
//}