package com.example.imdb.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.imdb.R;
import com.example.imdb.adapter.ViewPagerAdapter;
import com.example.imdb.model.Movie;
import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

public class FragmentDetail extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    YouTubePlayer player;
    Button btnWatchTrailer;
    ViewPagerAdapter adapter;
    Movie movie;
    ImageView imgBannerMovie;


    public FragmentDetail() {
    }

    public static FragmentDetail newInstance() {
        FragmentDetail f = new FragmentDetail();
        Bundle agrs = new Bundle();
        f.setArguments(agrs);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdetail, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnWatchTrailer = view.findViewById(R.id.btnWatchTrailer);
        Bundle bundle = getArguments();
        movie = (Movie) bundle.get("movie");
        imgBannerMovie = view.findViewById(R.id.imgBannerMovie);
        Picasso.with(getContext()).load(movie.getImage()).into(imgBannerMovie);
        btnWatchTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.youtubePlayerView, youTubePlayerFragment).commit();
                youTubePlayerFragment.initialize("AIzaSyC6xTGfXibZumAMpaFNsrYhIGuiJmQU_mk", new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        imgBannerMovie.setVisibility(View.GONE);
                        player = youTubePlayer;
                        player.loadVideo(movie.getTrailer());
                        player.play();
                        Log.i("btn watch trailer", "play video");
                        Log.i(movie.getId() + "", movie.getName());
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

            }
        });

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpage);

        adapter = new ViewPagerAdapter(getChildFragmentManager(), 0);


        setAdapterViewPage();

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.milkshake);
        //animation.setRepeatCount();
        btnWatchTrailer.startAnimation(animation);
    }

    public void setAdapterViewPage() {
        FragmentInfo fragmentInfo = FragmentInfo.newInstance();
        fragmentInfo.getArguments().putSerializable("data", movie);
        FragmentRating fragmentRating = FragmentRating.newInstance();
        fragmentRating.getArguments().putSerializable("data", movie);
        adapter.addFragment(fragmentInfo, "Info");
        adapter.addFragment(fragmentRating, "Rating");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
