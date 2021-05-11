package com.example.topnews.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.newslibrary.CategoryRepository;
import com.example.newslibrary.Sources;
import com.example.newslibrary.listeners.APICallback;
import com.example.topnews.DatabaseHelper;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSource;
import com.example.topnews.models.NewsSources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CategoryActivityRepository {

    private static CategoryActivityRepository instance;
    private MutableLiveData<Boolean> loader;

    //private CategoryRepository categoryRepository;

    private CategoryActivityRepository(){
         loader = new MutableLiveData<>();
    }

    public void setLoader(MutableLiveData<Boolean> loader) {
        this.loader = loader;
    }

    public static CategoryActivityRepository getInstance() {
        if (instance == null){
            instance = new CategoryActivityRepository();
        }
        return instance;
    }

    public void newsSources(MutableLiveData<List<NewsSources>> mLiveData, MutableLiveData<String> errorLiveData){
        loader.postValue(true);
        CategoryRepository.getInstance().getNewsSources(AppConstants.API_KEY, new APICallback() {
            @Override
            public void onSuccess(Object response) {
                loader.postValue(false);
                List<NewsSources> mList = (List<NewsSources>) response;
                Type token = new TypeToken<List<NewsSources>>(){}.getType();
                String temp = new Gson().toJson(mList);
                List<NewsSources> sources = new Gson().fromJson(temp,token);
               // RealmHelper.getInstance().categorySaveNews(sources);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (NewsSources src :
                                sources) {
                            // RealmHelper.getInstance().saveNews(art);
                            DatabaseHelper.getInstance().insertSourcesData(src);
                        }
                        mLiveData.postValue(sources);
                    }
                }).start();

            }

            @Override
            public void onFailure(String error) {
                loader.postValue(false);
                errorLiveData.postValue(error);
            }
        });
    }
}

