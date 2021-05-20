package com.example.topnews.repositories;
import android.app.Application;
import android.util.Log;

import com.example.newslibrary.Articles;
import com.example.newslibrary.NewsRepository;
import com.example.newslibrary.listeners.APICallback;
import com.example.topnews.models.NewsArticles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import androidx.lifecycle.MutableLiveData;

public class NewsActivityRepository {

    private static NewsActivityRepository instance;
    private MutableLiveData<Boolean> loader;
    private DbRepo dbRepo;
    private Application application;

    private NewsActivityRepository(){
        loader = new MutableLiveData<>();
    }

    public void setLoader(MutableLiveData<Boolean> loader) {
        this.loader = loader;
    }

    public static NewsActivityRepository getInstance(){
        if (instance == null){
            instance = new NewsActivityRepository();
        }
        return instance;

    }

    public void newsFromId(String source, MutableLiveData<String> mLiveData, MutableLiveData<String> errorLiveData){
        loader.postValue(true);
        NewsRepository.getInstance().newsFromId(source, new APICallback() {
            @Override
            public void onSuccess(Object response) {
                loader.postValue(false);
                List<Articles> mList = (List<Articles>) response;
                Type token = new TypeToken<List<NewsArticles>>(){}.getType();
                String temp = new Gson().toJson(mList);
                List<NewsArticles> articles = new Gson().fromJson(temp,token);
                for (NewsArticles art :
                        articles) {
                    art.setNewsId(source);
                }
                dbRepo = new DbRepo(application);
                dbRepo.insert(articles);
                mLiveData.postValue(source);
                Log.e("newsFromId", "onSuccess: ");
            }

            @Override
            public void onFailure(String error) {
                loader.postValue(false);
                errorLiveData.postValue(error);
            }
        });
    }
}
