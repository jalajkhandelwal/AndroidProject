package com.example.topnews.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.retrofit.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivityRepository {

    private static CategoryActivityRepository instance;
    private MutableLiveData<Boolean> loader;

    public void setLoader(MutableLiveData<Boolean> loader) {
        this.loader = loader;
    }

    private CategoryActivityRepository(){

    }

    public static CategoryActivityRepository getInstance() {
        if (instance == null){
            instance = new CategoryActivityRepository();
        }
        return instance;
    }

    public void getNewsSources(String apiKey, MutableLiveData<List<Sources>> mLiveData, MutableLiveData<String> errorLiveData){
        loader.postValue(true);
        Call<SourceResponse> call = ApiClient.getInstance().getApi().getSource(apiKey);
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                loader.postValue(false);
                if (response.isSuccessful() && response.body().getSources() != null) {
                    mLiveData.postValue(response.body().getSources());
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                loader.postValue(false);
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}

