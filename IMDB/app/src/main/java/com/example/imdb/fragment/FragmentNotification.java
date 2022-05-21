package com.example.imdb.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.AdapterWatchList;
import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterHomeFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment implements PresenterHomeFragment.IOnHomeFragment {
    AdapterWatchList adapter;
    RecyclerView rv;
    MainActivity mainActivity;
    List<Movie> list;
    PresenterHomeFragment presenter;

    public FragmentNotification() {
    }

    public static FragmentNotification newInstance() {
        FragmentNotification f= new FragmentNotification();
        Bundle b= new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentnotification,container,false);
        rv=view.findViewById(R.id.rvWatchList);
        mainActivity= (MainActivity) getActivity();
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Danh sách ưa thích");
        presenter= new PresenterHomeFragment(this);
        adapter= new AdapterWatchList(mainActivity,getContext());
        User user= (User) getArguments().get("user");
        if(user==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Warning login")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mainActivity.gotoLoginActivity();
                        }
                    }).setMessage("You must login first");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            String jsonUser= (new Gson()).toJson(user);
            list=user.getUserWatchList();
            adapter.setData(user.getUserWatchList());
            LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
            rv.setLayoutManager(manager);
            rv.setAdapter(adapter);
            adapter.setiOnClickItemWatchList(new AdapterWatchList.IOnClickItemWatchList() {
                @Override
                public void onClickNameMovie(Movie m) {
                    mainActivity.gotoFragmentDetails(m);
                }

                @Override
                public void onClickImage(Movie m) {
                    mainActivity.gotoFragmentDetails(m);
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClickRemove(Movie m) {
                    presenter.removeMovieWatchList(m.getId(),user.getId());
                    list.removeIf(m1-> m1.getId()==m.getId());
                    user.setUserWatchList(list);
                    updateShareReferences(user);
                }
            });
        }



        return view;


    }

    private void updateShareReferences(User user) {
        String jsonUser=(new Gson()).toJson(user);
        MainActivity.editor.putString("user",jsonUser);
        MainActivity.editor.apply();
    }

    @Override
    public void onGetData(List<Category> list) {

    }

    @Override
    public void addMovieWatchList(int id) {

    }

    @Override
    public void removeMovieWatchListResult(int result) {
        adapter.setData(list);

    }
}
