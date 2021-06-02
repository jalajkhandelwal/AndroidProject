package com.example.topnews.ViewModel;

import android.app.Application;

import com.example.topnews.models.NewsArticles;
import com.example.topnews.repositories.DbRepo;
import com.example.topnews.repositories.NewsActivityRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> mNewsLiveData;
    private MutableLiveData<String> mErrorLiveData;
    private NewsActivityRepository mRepository;
    private MutableLiveData<Boolean> mLoader;
    private DbRepo dbRepo;
    private MutableLiveData<List<NewsArticles>> getAllArticles;

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
        getAllArticles = new MutableLiveData<>();
    }

    public void initRepo(Application application){
        dbRepo = new DbRepo(application);
    }

    public void insert(List<NewsArticles> articles){
        dbRepo.insert(articles);
    }

    public MutableLiveData<List<NewsArticles>> getAllArticlesLiveData(){
        return getAllArticles;
    }

    public void getArticlesById(String newsId){
        dbRepo.getArticlesByNewsId(newsId,getAllArticles);
    }

    public MutableLiveData<String> getNewsListObserver() {
        return mNewsLiveData;
    }

    public void getNews(String source) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mRepository.setLoader(mLoader);
        mRepository.newsFromId(source,mNewsLiveData,mErrorLiveData);
    }

}
