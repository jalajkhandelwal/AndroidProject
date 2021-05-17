package com.example.topnews.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.topnews.dao.NewsDao;
import com.example.topnews.database.NewsDatabase;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;

import java.util.List;

public class DbRepo {

    private NewsDatabase newsDatabase;
    private LiveData<List<NewsArticles>> getAllArticles;
    private LiveData<List<NewsSources>> getAllSources;

    public DbRepo(Application application){
        newsDatabase = newsDatabase.getInstance(application);
        getAllArticles = newsDatabase.newsDao().getAllArticles();
        getAllSources = newsDatabase.newsDao().getAllSources();
    }

    public void insert(List<NewsArticles> articles){
        new InsertAstncTask(newsDatabase).execute(articles);
    }

    public LiveData<List<NewsArticles>> getAllArticles(){
        return getAllArticles;
    }

    static class InsertAstncTask extends AsyncTask<List<NewsArticles>,Void,Void> {

        private NewsDao newsDao;

        public InsertAstncTask(NewsDatabase newsDatabase) {
            newsDao = newsDatabase.newsDao();
        }

        @Override
        protected Void doInBackground(List<NewsArticles>... lists) {
            newsDao.insert(lists[0]);
            return null;
        }
    }

    public void insertSources(List<NewsSources> sources){
        new InsertSourcesAstncTask(newsDatabase).execute(sources);
    }

    public LiveData<List<NewsSources>> getAllSources(){
        return getAllSources;
    }

    static class InsertSourcesAstncTask extends AsyncTask<List<NewsSources>,Void,Void> {

        private NewsDao newsDao;

        public InsertSourcesAstncTask(NewsDatabase newsDatabase) {
            newsDao = newsDatabase.newsDao();
        }

        @Override
        protected Void doInBackground(List<NewsSources>... lists) {
            newsDao.insertSources(lists[0]);
            return null;
        }
    }
}
