package com.example.imdb.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentUser extends Fragment implements View.OnClickListener {
    MainActivity mainActivity;
    GoogleSignInAccount account;
    CircleImageView img;
    TextView tv;
    ImageButton btnSignout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public FragmentUser() {
    }

    public static FragmentUser newInstance() {
        FragmentUser f = new FragmentUser();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mainActivity= (MainActivity) getActivity();
        //User user=(User)bundle.get("user");
        img = view.findViewById(R.id.imgAvatar);
        tv = view.findViewById(R.id.tvUsername);
        btnSignout = view.findViewById(R.id.btnSignout);
        sharedPreferences = mainActivity.sharedPreferences;
        editor = mainActivity.editor;
        Gson gson = new Gson();
        User user= gson.fromJson(sharedPreferences.getString("user",""),User.class);
        tv.setText(user.getFullname());
        if (user.getAvatar().length() == 0) {

            img.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
        }
        else
             Picasso.with(getContext()).load(user.getAvatar()).into(img);
        btnSignout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignout:
                editor.clear().commit();
                mainActivity.getFragment(FragmentLogin.newInstance());
        }
    }
}
