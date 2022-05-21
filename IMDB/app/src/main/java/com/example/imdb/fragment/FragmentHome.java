package com.example.imdb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.AdapterSliderViewPager2;
import com.example.imdb.adapter.CategoryAdapter;
import com.example.imdb.model.Category;
import com.example.imdb.model.Movie;
import com.example.imdb.model.MovieWatchList;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterHomeFragment;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentHome extends Fragment implements PresenterHomeFragment.IOnHomeFragment {
    private ShimmerRecyclerView rvCategory;
    private PresenterHomeFragment presenterHomeFragment;
    private CategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private AdapterSliderViewPager2 adapterSliderViewPager2;
    private Handler handler= new Handler(Looper.getMainLooper());
    private Runnable runnable= new Runnable() {
        @Override
        public void run() {
            int currentItem=viewPager2.getCurrentItem();
            if(adapterSliderViewPager2.getData()==null) return;
            if(currentItem==adapterSliderViewPager2.getData().size()-1){
                viewPager2.setCurrentItem(0,true);
            }else viewPager2.setCurrentItem(currentItem+1,true);
        }
    };


    public FragmentHome() {
    }
    public static FragmentHome newInstance(){
        FragmentHome f= new FragmentHome();
        Bundle args= new Bundle();
        f.setArguments(args);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmenthome,container,false);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("IMDB");
        rvCategory= view.findViewById(R.id.rvCategory);
        rvCategory.showShimmerAdapter();
        progressBar=view.findViewById(R.id.progressbar);
        circleIndicator3=view.findViewById(R.id.circleindicator);
        viewPager2= view.findViewById(R.id.viewpagerSlider);

        adapterSliderViewPager2= new AdapterSliderViewPager2(getContext());

        presenterHomeFragment= new PresenterHomeFragment(this);
        progressBar.setVisibility(View.VISIBLE);
        presenterHomeFragment.onGetAllCategory();

        categoryAdapter= new CategoryAdapter(getContext(),(MainActivity) getActivity(),this);

        return view;
    }

    @Override
    public void onGetData(List<Category> list) {
        progressBar.setVisibility(View.GONE);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        CompositePageTransformer composite= new CompositePageTransformer();
        composite.addTransformer(new MarginPageTransformer(40));

        composite.addTransformer (new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });


        viewPager2.setPageTransformer(composite);


        adapterSliderViewPager2.setData(list.get(0).getMovies());
        viewPager2.setAdapter(adapterSliderViewPager2);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvCategory.hideShimmerAdapter();
        categoryAdapter.setData(list);
        rvCategory.setLayoutManager(linearLayoutManager);
        rvCategory.setAdapter(categoryAdapter);


    }

    @Override
    public void addMovieWatchList(int id) {
        Log.e("Add Success",id+"");
    }

    @Override
    public void removeMovieWatchListResult(int result) {
        Log.e("Remove Success",result+"");
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }

    public void addMovieWatchList(Movie m) {
        User user= (new Gson()).fromJson(MainActivity.sharedPreferences.getString("user",""),User.class);
        Date now= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String add_time=sdf.format(now);
        MovieWatchList movieWatchList= new MovieWatchList(add_time,m,user);
        presenterHomeFragment.addMovieWatchList(movieWatchList);
        Toast.makeText(getContext(),"Added the movie to watch list",Toast.LENGTH_SHORT).show();
    }

    public void removeMovieWatchList(int id_movie, int id_user) {
        presenterHomeFragment.removeMovieWatchList(id_movie, id_user);
        Toast.makeText(getContext(),"Removed the movie to watch list",Toast.LENGTH_SHORT).show();
    }
}
