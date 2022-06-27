package com.example.imdb.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.MovieCategoryAdapter;
import com.example.imdb.model.Movie;

import java.util.List;

public class FragmentSearchResult extends Fragment {
    private RecyclerView rv;
    private MovieCategoryAdapter adapter;
    private List<Movie> mList;

    public FragmentSearchResult(){}

    public static FragmentSearchResult newInstance(){
        FragmentSearchResult f= new FragmentSearchResult();
        Bundle b= new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_result,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Kết quả tìm kiếm");
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv= view.findViewById(R.id.rvSearchResult);
        mList= (List<Movie>) getArguments().get("data");
        adapter= new MovieCategoryAdapter(getContext(),(MainActivity) getActivity());
        adapter.setData(mList);
        GridLayoutManager layoutManager= new GridLayoutManager(getContext(),3,RecyclerView.VERTICAL,false);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        adapter.setIOnClickItem(new MovieCategoryAdapter.IOnClickItem() {
            @Override
            public void onClickImage(Movie m) {
                ((MainActivity) getActivity()).gotoFragmentDetails(m);
            }

            @Override
            public void onClickName(int position) {

            }

            @Override
            public void onClickAddWatchList(Movie m) {

            }

            @Override
            public void onRemoveMovieWatchList(int id_movie, int id_user) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getContext(),"Back pressed",Toast.LENGTH_SHORT).show();
                getChildFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
