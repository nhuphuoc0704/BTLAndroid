package com.example.imdb.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.model.RealPathUtil;
import com.example.imdb.model.User;

import com.example.imdb.presenter.PresenterUpLoadAvatar;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.internal.Intrinsics;

public class FragmentUser extends Fragment implements View.OnClickListener, PresenterUpLoadAvatar.IOnUpLoadAvatar {
    private static final int MY_REQUEST_CODE = 1115;
    MainActivity mainActivity;
    GoogleSignInAccount account;
    CircleImageView img;
    TextView tv;
    ImageButton btnSignout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageButton imgbtnChangeAvatar;
    Uri mUri;
    PresenterUpLoadAvatar presenter;
    User mUser;
    ProgressDialog progressDialog;
    Gson gson;

    public FragmentUser() {
    }


    public static FragmentUser newInstance() {
        FragmentUser f = new FragmentUser();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data= result.getData();
                        Uri uri= data.getData();
                        mUri=uri;
                        upLoadImage();

//                        try {
//                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
//                            img.setImageBitmap(bitmap);
//                            String strRealPath= RealPathUtil.getRealPath(getContext(),uri);
//                            Log.e("Uri real path",strRealPath);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            });

    private void upLoadImage() {
        String strRealPath= RealPathUtil.getRealPath(getContext(),mUri);
        progressDialog.show();
        presenter.upLoadAvatar(mUser.getId(),strRealPath);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Trang cá nhân");
        Bundle bundle = getArguments();
        mainActivity= (MainActivity) getActivity();
        //User user=(User)bundle.get("user");
        img = view.findViewById(R.id.imgAvatar);
        tv = view.findViewById(R.id.tvUsername);
        imgbtnChangeAvatar= view.findViewById(R.id.imgbtnChangeAvatar);
        btnSignout = view.findViewById(R.id.btnSignout);
        sharedPreferences = mainActivity.sharedPreferences;
        editor = mainActivity.editor;
        presenter= new PresenterUpLoadAvatar(this);
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading");

        gson= new Gson();
        mUser= gson.fromJson(sharedPreferences.getString("user",""),User.class);
        tv.setText(mUser.getFullname());
        if (mUser.getAvatar()==null||mUser.getAvatar().length() == 0) {

            img.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
        }
        else
             Picasso.with(getContext()).load(mUser.getAvatar()).into(img);
        btnSignout.setOnClickListener(this);
        imgbtnChangeAvatar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignout:
                editor.clear().commit();
                mainActivity.getFragment(FragmentLogin.newInstance());
                break;
            case R.id.imgbtnChangeAvatar:
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .createIntent(intent -> {
                            mActivityResultLauncher.launch(intent);
                            return null;
                        });
//                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//                    openGallery();
//                }
//                else {
//                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                    requestPermissions(permissions,MY_REQUEST_CODE);
//
//                }
                break;
        }
    }

    @Override
    public void upLoadSuccess(String result) {
        progressDialog.dismiss();
        progressDialog.setMessage("Updating please wait...");
        progressDialog.show();
        mUser.setAvatar(result);
        presenter.updateAvatar(mUser);

    }

    @Override
    public void updateAvatar(int rs) {
        progressDialog.dismiss();
        Picasso.with(getContext()).load(mUser.getAvatar()).into(img);
        Toast.makeText(getContext(),"Cập nhật ảnh đại diện thành công",Toast.LENGTH_SHORT).show();
        setSharedPreferences();
    }

    private void setSharedPreferences() {
        String jsonUser= gson.toJson(mUser);
        editor.putString("user",jsonUser);
        editor.commit();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==MY_REQUEST_CODE){
//            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                openGallery();
//            }
//        }
//
//    }
//
//    private void openGallery() {
//        Intent intent= new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select a picture"));
//
//    }
}
