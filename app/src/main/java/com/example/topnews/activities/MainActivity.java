package com.example.topnews.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.topnews.Model.Articles;
import com.example.topnews.R;
import com.example.topnews.adapters.NewsRecyclerViewAdapter;
import com.example.topnews.fragments.DrawerFragment;
import com.example.topnews.interfces.NewsIdListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NewsIdListener {

    private Toolbar toolbar;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;
    private List<Articles> articles = new ArrayList<>();
    private RecyclerView mNewsRecyclerView;
    private DrawerFragment mDrawerFragment;


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
        getNewsById(id);
    }

    private void getNewsById(String id) {

    }
}