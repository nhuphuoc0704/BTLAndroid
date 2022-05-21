package com.example.imdb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imdb.model.InvalidData;
import com.example.imdb.model.User;
import com.example.imdb.presenter.PresenterUser;
import com.example.imdb.services.MyService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.Serializable;
import java.nio.file.attribute.UserPrincipal;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PresenterUser.IOnUser {
    private Button btnLogin, btnRegistry;
    private TextInputEditText edtUsername, edtPassword;
    private TextView tvRegistry;
    private GoogleSignInClient mGoogleSignInClient;
    private PresenterUser presenter;
    private EditText edtUsernameReg, edtPasswordReg, edtConfirm, edtEmail, edtFullname;
    private TextView tvCancel;
    private Dialog dialog;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        btnLogin = findViewById(R.id.btnLoginActivity);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        tvRegistry = findViewById(R.id.tvRegistry);

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        presenter = new PresenterUser(this);

        tvRegistry.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signin();
                break;
            case R.id.btnLoginActivity:
                String username = edtUsername.getText() + "";
                String password = edtPassword.getText() + "";
                if (username.length() == 0 || password.length() == 0)
                    Toast.makeText(getBaseContext(), "Tài khoản mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                else
                {

                    progressDialog.show();
                    presenter.checkLogin(username, password);
                }

                break;
            case R.id.tvRegistry:

                dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_registry);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getAttributes().windowAnimations = R.style.MyDialogAnimation;
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);
                dialog.setCancelable(false);
                dialog.show();

                tvCancel = dialog.findViewById(R.id.tvCancelResgistry);
                btnRegistry = dialog.findViewById(R.id.btnRegistry);
                edtUsernameReg = dialog.findViewById(R.id.edtUsernameRegistry);
                edtPasswordReg = dialog.findViewById(R.id.edtPasswordRegistry);
                edtConfirm = dialog.findViewById(R.id.edtConfirmPassRegistry);
                edtFullname = dialog.findViewById(R.id.edtFullname);
                edtEmail = dialog.findViewById(R.id.edtEmail);
                btnRegistry.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String email = edtEmail.getText() + "";
                        String unameregistry = edtUsernameReg.getText() + "";
                        String password = edtPasswordReg.getText() + "";
                        String fullname = edtFullname.getText() + "";
                        String confirm = edtConfirm.getText() + "";
                        String noti = new InvalidData(unameregistry, password, confirm, fullname, email).checkIsValid();
                        if (!noti.equals("OK")) {
                            Toast.makeText(getBaseContext(), noti, Toast.LENGTH_LONG).show();
                        } else {
                            User u = new User(unameregistry, email, password, fullname, "");
                            presenter.registry(u);
                        }
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                break;


        }
    }

    private void signin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            mGoogleSignInClient.signOut();
        }
    }

    private void resultset(User u) {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("user", u);
        i.putExtra("rs", b);
        setResult(RESULT_OK, i);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI
            String username = account.getEmail();
            String password = account.getId();
            String avatar = account.getPhotoUrl() + "";
            String fullname = account.getDisplayName() + account.getFamilyName();
            User u = new User(username, username, password, fullname, avatar);
            presenter.loginByGmail(u);
            //resultset(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            //updateUI(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resultset(null);
    }

    @Override
    public void onGetUser(User u) {

    }

    @Override
    public void checkLogin(User u) {
        progressDialog.dismiss();
        if (u != null) {
            Log.e("User login success", u.getUserWatchList().size() + "");
            resultset(u);
        } else
            Toast.makeText(getBaseContext(), "Username or password invalid", Toast.LENGTH_LONG).show();
    }

    @Override
    public void registry(User u) {
        if (u == null) {
            Toast.makeText(getBaseContext(), "Tên đăng nhập đã tồn tại!!!", Toast.LENGTH_LONG).show();
        } else {
            Intent intend1 = new Intent(this, MyService.class);
            Bundle b = new Bundle();
            b.putSerializable("user", u);
            intend1.putExtras(b);
            startService(intend1);
            dialog.dismiss();
        }


    }

    @Override
    public void loginByGmail(User u) {
        Log.e("Login success", u.getUserWatchList().size() + "");
        resultset(u);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(this, MyService.class);
        stopService(i);
    }
}