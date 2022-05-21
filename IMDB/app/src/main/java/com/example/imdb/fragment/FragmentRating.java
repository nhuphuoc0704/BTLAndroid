package com.example.imdb.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.AdapterComment;
import com.example.imdb.model.Comment;
import com.example.imdb.model.MessageEvent;
import com.example.imdb.model.Movie;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterComment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class FragmentRating extends Fragment implements PresenterComment.IOnComment {
    private TextView tvRatings, tvCancel;
    private EditText edtTitle, edtContent;
    private Button btnAddRating, btnSubmit;
    private RecyclerView rv;
    private PresenterComment presenter;
    private ProgressBar progressBar;
    private AdapterComment adapter;
    private Dialog dialog;
    private RatingBar ratingBar;
    private User user;
    private Movie movie;
    private Comment mComment;
    private float star;
    private List<Comment> mListComment;
    private MainActivity mainActivity;
    private final Gson gson = new Gson();

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
        mainActivity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        movie = (Movie) bundle.get("data");
        progressBar.setVisibility(View.VISIBLE);
        presenter = new PresenterComment(this);
        presenter.getAllCommentOfMovie(movie.getId());
        adapter = new AdapterComment(getContext());
        adapter.setOnClickOption(new AdapterComment.IOnClickOption() {
            @Override
            public void onClickOption(int position, View v) {
                mComment = adapter.getData().get(position);
                registerForContextMenu(v);
            }
        });
        String jsonUser = MainActivity.sharedPreferences.getString("user", null);
        user = gson.fromJson(jsonUser, User.class);
        tvRatings.setText(movie.getRatings() + " Ratings");

        btnAddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (user == null) {
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
                            }).setMessage("You must login to submit a rating");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {

                    addRating();
                }

            }
        });


    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Chỉnh sửa");
        menu.add("Xóa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Chỉnh sửa")) {
            editRating();
        } else if (item.getTitle().equals("Xóa")) {
            Log.e(FragmentRating.class.getName(),"Delete rating");

        }

        return true;

    }

    private void addRating() {
        creatDialogRating();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRating();

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                star = v;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void creatDialogRating() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating_moive);

        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.MyDialogAnimation;
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.show();


        tvCancel = dialog.findViewById(R.id.tvCancelRating);
        edtTitle = dialog.findViewById(R.id.edtTitleComment);
        edtContent = dialog.findViewById(R.id.edtContent);
        ratingBar = dialog.findViewById(R.id.ratingBar);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);

    }

    private void editRating() {
        creatDialogRating();

        edtTitle.setText(mComment.getTitle());
        edtContent.setText(mComment.getContent());
        ratingBar.setRating(mComment.getStar());
        float oldStar= mComment.getStar();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEditRating(oldStar);
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                star = v;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void submitEditRating(float oldStar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String title = edtTitle.getText() + "";
        String content = edtContent.getText() + "";
        Date currentTime = Calendar.getInstance().getTime();
        String create_time = sdf.format(currentTime);
        mComment.setStar(star);
        mComment.setTitle(title);
        mComment.setContent(content);
        mComment.setCreated_time(create_time);
        mComment.setUser(user);
        mComment.setMovie(movie);

        presenter.editRating(mComment,oldStar);



    }

    private void submitRating() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String title = edtTitle.getText() + "";
        String content = edtContent.getText() + "";
        Date currentTime = Calendar.getInstance().getTime();
        String create_time = sdf.format(currentTime);
        mComment = new Comment(title, content, create_time, star);
        mComment.setUser(user);
        mComment.setMovie(movie);
        presenter.addRating(mComment);

    }

    private void initView(View view) {
        tvRatings = view.findViewById(R.id.tvRatings);
        btnAddRating = view.findViewById(R.id.btnRating);
        rv = view.findViewById(R.id.rvComment);
        progressBar = view.findViewById(R.id.progressbarRatingFragment);
    }

    @Override
    public void getAllCommentOfMovie(List<Comment> list) {
        progressBar.setVisibility(View.GONE);
        if (list != null) {
            mListComment = list;
            adapter.setData(list);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rv.setLayoutManager(manager);
            rv.setAdapter(adapter);

        }
    }

    @Override
    public void onRatingSuccess(Comment comment) {
        btnAddRating.setEnabled(false);
        mComment = comment;
        mListComment.add(mComment);
        adapter.setData(mListComment);
        String cmtGson = gson.toJson(comment.getMovie());
        EventBus.getDefault().post(new MessageEvent(cmtGson));
        dialog.dismiss();
    }

    @Override
    public void editRating(Comment cmt) {
            dialog.dismiss();
            adapter.updateEditRating(cmt);

    }
}
