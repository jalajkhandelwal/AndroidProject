package com.example.topnews;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;

import com.example.topnews.interfces.AsyncNewsListener;
import com.example.topnews.models.NewsArticles;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskRunner extends AsyncTask<String,Void,Void> {

    private DatabaseHelper db;
    private String source;
    private AsyncNewsListener asyncNewsListener;

    private List<NewsArticles> articles = new ArrayList<>();
    private MutableLiveData<String> mLiveData;
    private MutableLiveData<String> mErrorLiveData;

    public AsyncTaskRunner(String source, List<NewsArticles> articles,AsyncNewsListener asyncNewsListener) {
        this.source = source;
        this.asyncNewsListener = asyncNewsListener;
        this.articles = articles;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        asyncNewsListener.onDbOperationComplete();
    }

    @Override
    protected Void doInBackground(String... strings) {
        for (NewsArticles art :
                articles) {
            art.setNewsId(source);
            DatabaseHelper.getInstance().insertData(art);
        }
        return null;
    }
}
