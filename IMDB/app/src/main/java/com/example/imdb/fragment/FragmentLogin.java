package com.example.imdb.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.imdb.MainActivity;
import com.example.imdb.R;

public class FragmentLogin extends Fragment {
    MainActivity mainActivity;
    public FragmentLogin(){}

    public static FragmentLogin newInstance(){
        FragmentLogin f= new FragmentLogin();
        Bundle agrs= new Bundle();
        f.setArguments(agrs);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        Button btnLogin= view.findViewById(R.id.btnLogin);
        mainActivity= (MainActivity) getActivity();
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Đăng nhập");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.gotoLoginActivity();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                // Get the layout inflater
//                LayoutInflater inflater = requireActivity().getLayoutInflater();
//                builder.setView(R.layout.dialog_registry);
//                builder.create().show();


            }
        });

        if(mainActivity.account!=null){
            Log.i("Login by:",mainActivity.account.getEmail());
        }


        return view;
    }
}
