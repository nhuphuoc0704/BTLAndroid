package com.example.imdb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imdb.R;

public class FragmentNotification extends Fragment {
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
        return inflater.inflate(R.layout.fragmentnotification,container,false);
    }
}
