package com.example.topnews.ViewModel;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.retrofit.ApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Articles>> mNewsLiveData;

    public NewsViewModel() {
        mNewsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Articles>> getNewsListObserver() {
        return mNewsLiveData;
    }

    public void getNews(String source){
        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(source, AppConstants.API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body()!= null &&response.body().getArticles() != null) {
                    mNewsLiveData.postValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                //Toast.makeText(,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
