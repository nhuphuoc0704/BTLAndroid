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
import com.example.imdb.model.Movie;

public class FragmentInfo extends Fragment {
    public TextView tvName, tvDirector, tvStoryline, tvWriter, tvStar, tvDuration, tvRelease_date, tvStars;
    private Movie m;

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
        m = (Movie) bundle.get("data");
        Log.i(FragmentInfo.class.getName(), m.getName());
        tvDirector = v.findViewById(R.id.tvDirector);
        tvName = v.findViewById(R.id.tvNameMovieInfo);
        tvDuration = v.findViewById(R.id.tvDuration);
        tvRelease_date = v.findViewById(R.id.tvRelease_date);
        tvStar = v.findViewById(R.id.tvStar);
        tvStars = v.findViewById(R.id.tvStars);
        tvStoryline = v.findViewById(R.id.tvStoryline);
        tvWriter = v.findViewById(R.id.tvWriters);


        tvName.setText(m.getName());
        tvWriter.setText(m.getWriters());
        tvStoryline.setText(m.getStoryline());
        tvStar.setText(m.getStar() + "");
        tvDuration.setText(m.getDuration());
        tvRelease_date.setText(m.getRelease_date());
        tvStars.setText(m.getStars());
        tvDirector.setText(m.getDirector());

    }

    private void init(View v) {

    }
}
