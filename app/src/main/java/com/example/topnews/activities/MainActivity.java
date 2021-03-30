package com.example.topnews.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.R;
import com.example.topnews.ViewModel.NewsViewModel;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.adapters.NewsRecyclerViewAdapter;
import com.example.topnews.fragments.DrawerFragment;
import com.example.topnews.interfces.NewsIdListener;
import com.example.topnews.retrofit.ApiClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsIdListener {

    private Toolbar toolbar;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private CategoryRecyclerViewAdapter mCategoryRecyclerViewAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;
    private List<Articles> articles = new ArrayList<>();
    private RecyclerView mNewsRecyclerView;
    private DrawerFragment mDrawerFragment;
    final String API_KEY = "14bfe05834a444b79739ac49197ae489";
    private NewsViewModel mNewsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolb);
        mNewsRecyclerView = findViewById(R.id.rv_news);
        initRecyclerView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class); //binding viewModel with activity and then passing it to recycle view
        mNewsViewModel.getNewsListObserver().observe(this, new Observer<List<Sources>>() {
            @Override
            public void onChanged(List<Sources> sources) {
                mCategoryRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        mNavigationView = findViewById(R.id.drawer_container);
        mFrameLayout = findViewById(R.id.fl_container);
        mDrawerFragment = new DrawerFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.drawer_container, mDrawerFragment ).commit();
        mDrawerFragment.setNewsIdListener(this);
    }

    private void initRecyclerView() {
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(articles);
        mNewsRecyclerView.setAdapter(mNewsRecyclerViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("TAG", "onOptionsItemSelected: ");
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }


    @Override
    public void onNewsIdReceived(String id) {
        if (mDrawerLayout != null){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        Log.i("TAG", "onNewsIdReceived: " + id);
        getNewsById(id,API_KEY);
    }

    private void getNewsById(String id,String apiKey) {
        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(id, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles.addAll(response.body().getArticles());
                    mNewsRecyclerViewAdapter.notifyDataSetChanged();
                    Log.e("TAG", "onResponse: " + articles.size());
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}