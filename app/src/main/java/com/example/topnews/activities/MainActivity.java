package com.example.topnews.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.R;
import com.example.topnews.adapters.NewsRecyclerViewAdapter;
import com.example.topnews.fragments.DrawerFragment;
import com.example.topnews.fragments.NewsFragment;
import com.example.topnews.retrofit.ApiClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    final String API_KEY = "14bfe05834a444b79739ac49197ae489";
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;
    private List<Articles> articles = new ArrayList<>();
    private DrawerFragment mDrawerFragment;
    private NewsFragment mNewsFragment;
    //Context context;
    private static final String Key = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNavigationView = findViewById(R.id.drawer_container);
        mFrameLayout = findViewById(R.id.fl_container);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,new NewsFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.drawer_container,new DrawerFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("TAG", "onOptionsItemSelected: " );
            if (mDrawerLayout.isDrawerOpen(mNavigationView)){
                mDrawerLayout.closeDrawer(Gravity.START);
            } else {
                mDrawerLayout.openDrawer(Gravity.START);
            }
            return true;
    }

    public void retrievejson(String country, String apiKey){

        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {

            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    mNewsRecyclerViewAdapter.notifyDataSetChanged();
                     Gson gson = new Gson();
                     String json = gson.toJson(articles);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                     SharedPreferences.Editor editor = sp.edit();
                     editor.putString(Key,json);
                     editor.apply();
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String json = sp.getString(Key,"");
                articles.clear();
                List<Articles> art = new ArrayList<>();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Articles>>() {}.getType();
                art = gson.fromJson(json, type);
                articles.addAll(art);
                mNewsRecyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}