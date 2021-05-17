package com.example.topnews.ViewModel;

import android.app.Application;

import com.example.newslibrary.Articles;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.repositories.DbRepo;
import com.example.topnews.repositories.NewsActivityRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> mNewsLiveData;
    private MutableLiveData<String> mErrorLiveData;
    private NewsActivityRepository mRepository;
    private MutableLiveData<Boolean> mLoader;
    private DbRepo dbRepo;
    private LiveData<List<NewsArticles>> getAllArticles;

    public MutableLiveData<Boolean> getLoader() {
        return mLoader;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return mErrorLiveData;
    }

    public NewsViewModel() {
        mNewsLiveData = new MutableLiveData<>();
        mErrorLiveData = new MutableLiveData<>();
        mLoader = new MutableLiveData<>();
        mRepository = NewsActivityRepository.getInstance();
    }

    public void initRepo(Application application){
        dbRepo = new DbRepo(application);
        getAllArticles = dbRepo.getAllArticles();
    }

    public void insert(List<NewsArticles> articles){
        dbRepo.insert(articles);
    }

    public LiveData<List<NewsArticles>> getAllArticles(){
        return getAllArticles;
    }

    public MutableLiveData<String> getNewsListObserver() {
        return mNewsLiveData;
    }

    public void getNews(String source){
        mRepository.setLoader(mLoader);
        mRepository.newsFromId(source,mNewsLiveData,mErrorLiveData);
    }

}
