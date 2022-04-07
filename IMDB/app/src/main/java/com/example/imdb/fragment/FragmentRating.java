package com.example.imdb.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.imdb.LoginActivity;
import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.AdapterComment;
import com.example.imdb.adapter.ViewPagerAdapter;
import com.example.imdb.model.Comment;
import com.example.imdb.model.Movie;
import com.example.imdb.model.MyFragment;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterComment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class FragmentRating extends Fragment implements PresenterComment.IOnComment {
    private TextView tvRatings,tvCancel;
    private EditText edtTitle, edtContent;
    private Button btnAddRating,btnSubmit;
    private RecyclerView rv;
    private PresenterComment presenter;
    private ProgressBar progressBar;
    private AdapterComment adapter;
    private Dialog dialog;
    private RatingBar ratingBar;
    private  User user;
    private Movie movie;
    private float star;

    public FragmentRating() {
    }

    public static FragmentRating newInstance() {
        FragmentRating f = new FragmentRating();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_rating, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Bundle bundle= getArguments();
         movie= (Movie) bundle.get("data");
        progressBar.setVisibility(View.VISIBLE);
        presenter= new PresenterComment(this);
        presenter.getAllCommentOfMovie(movie.getId());
        adapter= new AdapterComment(getContext());

        btnAddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson= new Gson();
                String jsonUser= MainActivity.sharedPreferences.getString("user",null);
                if(jsonUser==null){
                    AlertDialog.Builder builder= new AlertDialog.Builder(getContext()).setTitle("Warning login")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setMessage("You must login to submit a rating");
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                }
                else{
                    user= gson.fromJson(jsonUser,User.class);
                    addRating();
                }

            }
        });



    }

    private void addRating() {
//        dialog= new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_registry);
//        dialog.show();
        //dialog.setCancelable(false);
        // Get the layout inflater
            dialog= new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_rating_moive);

                Window window= dialog.getWindow();
                if(window==null) return;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowAttributes= window.getAttributes();
                windowAttributes.gravity= Gravity.CENTER;
                window.setAttributes(windowAttributes);
                //dialog.setCancelable(t);
                dialog.show();


        tvCancel=dialog.findViewById(R.id.tvCancelRating);
        edtTitle=dialog.findViewById(R.id.edtTitleComment);
        edtContent= dialog.findViewById(R.id.edtContent);
        ratingBar= dialog.findViewById(R.id.ratingBar);
        btnSubmit=dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRating();

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                star=v;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




    }

    private void submitRating() {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String title= edtTitle.getText()+"";
        String content=edtContent.getText()+"";
        Date currentTime = Calendar.getInstance().getTime();
        String create_time=sdf.format(currentTime);
        Comment comment= new Comment(title,content,create_time,star);
        comment.setUser(user);
        comment.setMovie(movie);
        presenter.addRating(comment);

    }

    private void initView(View view) {
        tvRatings=view.findViewById(R.id.tvRatings);
        btnAddRating= view.findViewById(R.id.btnRating);
        rv= view.findViewById(R.id.rvComment);
        progressBar=view.findViewById(R.id.progressbarRatingFragment);
    }

    @Override
    public void getAllCommentOfMovie(List<Comment> list) {
        progressBar.setVisibility(View.GONE);
        if(list!=null){
            adapter.setData(list);
            LinearLayoutManager manager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
            rv.setLayoutManager(manager);
            rv.setAdapter(adapter);

        }
    }

    @Override
    public void onRatingSuccess(String results) {
        Toast.makeText(getContext(),results,Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
