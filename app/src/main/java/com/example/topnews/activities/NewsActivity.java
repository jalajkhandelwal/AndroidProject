package com.example.topnews.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.topnews.Model.Articles;
import com.example.topnews.R;
import com.example.topnews.ViewModel.NewsViewModel;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.adapters.NewsRecyclerViewAdapter;
import com.example.topnews.fragments.DrawerFragment;
import com.example.topnews.interfces.NewsIdListener;
import com.example.topnews.viewmodelfactories.NewsViewModelFactory;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsActivity extends AppCompatActivity implements NewsIdListener {

    private Toolbar toolbar;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private CategoryRecyclerViewAdapter mCategoryRecyclerViewAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;
    private List<Articles> articles = new ArrayList<>();
    private RecyclerView mNewsRecyclerView;
    private DrawerFragment mDrawerFragment;
    private NewsViewModel mNewsViewModel;
    //private LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initRecyclerView();
        initDrawer();
        initViewModel();
        setObservers();
    }

    private void initUI() {
        setContentView(R.layout.activity_news);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolb);
        mNewsRecyclerView = findViewById(R.id.rv_news);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mNavigationView = findViewById(R.id.drawer_container);
        mFrameLayout = findViewById(R.id.fl_container);
    }

    private void initRecyclerView() {
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(articles);
        mNewsRecyclerView.setAdapter(mNewsRecyclerViewAdapter);
    }

    private void initDrawer() {
        mDrawerFragment = new DrawerFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.drawer_container, mDrawerFragment ).commit();
        mDrawerFragment.setNewsIdListener(this);
    }

    private void initViewModel() {
        mNewsViewModel = new ViewModelProvider(this, new NewsViewModelFactory()).get(NewsViewModel.class);
    }

    private void setObservers() {
        mNewsViewModel.getNewsListObserver().observe(this, articles1 -> {
            Log.e("TAG", "onCreate: observer" );
            articles.clear();
            articles.addAll(articles1);
            mNewsRecyclerViewAdapter.notifyDataSetChanged();
        });

        mNewsViewModel.getLoader().observe(this,isShowLoader ->{

        });

        mNewsViewModel.getErrorLiveData().observe(this,message ->{

        });
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
        getNewsById(id);
    }

    private void getNewsById(String id) {
        mNewsViewModel.getNews(id);
    }
}