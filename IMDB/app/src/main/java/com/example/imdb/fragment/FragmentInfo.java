package com.example.imdb.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imdb.R;
import com.example.imdb.model.MessageEvent;
import com.example.imdb.model.Movie;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentInfo extends Fragment {
    public TextView tvName, tvDirector, tvStoryline, tvWriter, tvStar, tvDuration, tvRelease_date, tvStars;
    private Movie mMovie;

    public FragmentInfo() {

    }

    public static FragmentInfo newInstance() {
        FragmentInfo f = new FragmentInfo();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_movie, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Bundle bundle = getArguments();
        mMovie = (Movie) bundle.get("data");


        init(v);
        setData();

    }

    private void setData() {
        tvName.setText(mMovie.getName());
        tvWriter.setText(mMovie.getWriters());
        tvStoryline.setText(mMovie.getStoryline());
        tvStar.setText(mMovie.getStar() + "");
        tvDuration.setText(mMovie.getDuration());
        tvRelease_date.setText(mMovie.getRelease_date());
        tvStars.setText(mMovie.getStars());
        tvDirector.setText(mMovie.getDirector());
    }

    private void init(View v) {
        tvDirector = v.findViewById(R.id.tvDirector);
        tvName = v.findViewById(R.id.tvNameMovieInfo);
        tvDuration = v.findViewById(R.id.tvDuration);
        tvRelease_date = v.findViewById(R.id.tvRelease_date);
        tvStar = v.findViewById(R.id.tvStar);
        tvStars = v.findViewById(R.id.tvStars);
        tvStoryline = v.findViewById(R.id.tvStoryline);
        tvWriter = v.findViewById(R.id.tvWriters);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) {
        Gson gson = new Gson();
        Movie movieEvent = gson.fromJson(event.message, Movie.class);
        tvStar.setText(movieEvent.getStar()+"");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
