package com.example.topnews.ViewModel;

import com.example.topnews.Model.Articles;
import com.example.topnews.repositories.NewsActivityRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Articles>> mNewsLiveData;
    private MutableLiveData<String> mErrorLiveData;
    private NewsActivityRepository mRepository;
    private MutableLiveData<Boolean> mLoader;

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

    public MutableLiveData<List<Articles>> getNewsListObserver() {
        return mNewsLiveData;
    }

    public void getNews(String source){
        mRepository.setLoader(mLoader);
        mRepository.getNewsFromId(source,mNewsLiveData,mErrorLiveData);
    }

}
