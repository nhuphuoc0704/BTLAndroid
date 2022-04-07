package com.example.imdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.imdb.broadcast.receiver.InternetBroadcastReceiver;
import com.example.imdb.fragment.FragmentDetail;

import com.example.imdb.fragment.FragmentHome;
import com.example.imdb.fragment.FragmentLogin;
import com.example.imdb.fragment.FragmentNotification;
import com.example.imdb.fragment.FragmentRating;
import com.example.imdb.fragment.FragmentUser;
import com.example.imdb.model.BottomNavigationBehavior;
import com.example.imdb.model.Movie;
import com.example.imdb.model.SearchSuggestionProvider;
import com.example.imdb.model.User;

import com.example.imdb.presenter.PresenterSearchMovie;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, PresenterSearchMovie.IOnSearchable {
    BottomNavigationView bottomNav;
    public GoogleSignInAccount account;
    public User user;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";

    public InternetBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doMySearch(query);
        }

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNav.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNav.setSelectedItemId(R.id.ic_home);
        sharedPreferences= getBaseContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    private void doMySearch(String query) {
        PresenterSearchMovie presenterSearchMovie= new PresenterSearchMovie(this);
        presenterSearchMovie.searchable(query);

        //gotoLoginActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_item, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
       searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_home:
                getFragment( FragmentHome.newInstance());
                return true;
            case R.id.ic_notification:
                getFragment( FragmentNotification.newInstance());
                return true;
            case R.id.ic_user:
                if(sharedPreferences.getString("user","-1").equals("-1"))
                    getFragment( FragmentLogin.newInstance());
                else getFragment( FragmentUser.newInstance());
                return true;
        }
        return false;
    }
    public void gotoLoginActivity(){
        Intent i= new Intent(getBaseContext(),LoginActivity.class);
        startActivityForResult(i,11);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11){
            if(resultCode==RESULT_OK){
            Bundle b= data.getBundleExtra("rs");
            user=(User)b.get("user");

            if(user!=null){
                setSharePreferences(user);
                FragmentUser f= FragmentUser.newInstance();
                f.getArguments().putSerializable("user",user);
                getFragment(f);
            }
            //else bottomNav.setSelectedItemId(R.id.ic_home);
            }
        }
    }

    private void setSharePreferences(User user) {
        Gson gson= new Gson();
        String jsonUser=gson.toJson(user);
        editor.putString("user",jsonUser);
        Log.i("Save data ",user.toString());
        editor.apply();
    }

    public void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    public void gotoFragmentDetails(Movie m) {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        FragmentDetail fragmentDetail= FragmentDetail.newInstance();
        Bundle bundle= new Bundle();
        fragmentDetail.getArguments().putSerializable("movie",m);
        transaction.replace(R.id.container,fragmentDetail,FragmentDetail.class.getName()).addToBackStack(FragmentDetail.class.getName()).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(receiver);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //receiver=new InternetBroadcastReceiver();
        //registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void searchable(List<Movie> list) {
        Log.e(this.getClass().getName(),list.size()+"");
    }
}