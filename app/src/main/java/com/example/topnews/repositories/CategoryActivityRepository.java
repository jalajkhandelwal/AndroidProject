package com.example.topnews.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.newslibrary.CategoryRepository;
import com.example.newslibrary.Sources;
import com.example.newslibrary.listeners.APICallback;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class CategoryActivityRepository {

    private static CategoryActivityRepository instance;
    private MutableLiveData<Boolean> loader;
    private DbRepo dbRepo;
    private Application application;

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

    public void newsSources(MutableLiveData<List<Sources>> mLiveData, MutableLiveData<String> errorLiveData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        loader.postValue(true);
        CategoryRepository categoryRepository = new CategoryRepository();
        Class cls = categoryRepository.getClass();
        Constructor constructor = cls.getConstructor();
        Object myObj = constructor.newInstance();
        Method category = myObj.getClass().getMethod("getNewsSources");
        category.invoke(AppConstants.API_KEY, new APICallback() {
            @Override
            public void onSuccess(Object o) {
                loader.postValue(false);
                List<NewsSources> mList = (List<NewsSources>) o;
                Type token = new TypeToken<List<NewsSources>>() {
                }.getType();
                String temp = new Gson().toJson(mList);
                List<NewsSources> sources = new Gson().fromJson(temp, token);

                dbRepo = new DbRepo(application);
                dbRepo.insertSources(sources);
                Log.e("newSources", "onSuccess: ");
                mLiveData.postValue((List<Sources>) o);
            }

            @Override
            public void onFailure(String s) {
                loader.postValue(false);
                errorLiveData.postValue(s);
            }
        });
      /*  CategoryRepository.getInstance().getNewsSources(AppConstants.API_KEY, new APICallback() {
            @Override
            public void onSuccess(Object response) {
                loader.postValue(false);
                List<NewsSources> mList = (List<NewsSources>) response;
                Type token = new TypeToken<List<NewsSources>>(){}.getType();
                String temp = new Gson().toJson(mList);
                List<NewsSources> sources = new Gson().fromJson(temp,token);

                dbRepo = new DbRepo(application);
                dbRepo.insertSources(sources);
                Log.e("newSources", "onSuccess: ");
                mLiveData.postValue((List<Sources>) response);
            }

            @Override
            public void onFailure(String error) {
                loader.postValue(false);
                errorLiveData.postValue(error);
            }
        });*/
    }
}

