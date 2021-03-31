package com.example.topnews.repositories;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.retrofit.ApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivityRepository {

    private static NewsActivityRepository instance;
    private MutableLiveData<Boolean> loader;

    public void setLoader(MutableLiveData<Boolean> loader) {
        this.loader = loader;
    }

    private NewsActivityRepository(){

    }

    public static NewsActivityRepository getInstance(){
        if (instance == null){
            instance = new NewsActivityRepository();
        }
        return instance;

    }

    public void getNewsFromId(String id, MutableLiveData<List<Articles>> mLiveData, MutableLiveData<String> errorLiveData){
        loader.postValue(true);
        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(id, AppConstants.API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                loader.postValue(false);
                if (response.isSuccessful() && response.body()!= null &&response.body().getArticles() != null) {
                    mLiveData.postValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                loader.postValue(false);
                errorLiveData.postValue(t.getMessage());
            }
        });
    }






}
