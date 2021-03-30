package com.example.topnews.ViewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private CategoryRecyclerViewAdapter madapter;
    private static final String Key = "key";
    final String API_KEY = "14bfe05834a444b79739ac49197ae489";

    private MutableLiveData<List<Sources>> msources;

    public NewsViewModel() {
        msources = new MutableLiveData<>();
    }

    public MutableLiveData<List<Sources>> getNewsListObserver() {
        return msources;
    }

    public void NewsSources(String apiKey){
        Call<SourceResponse> call = ApiClient.getInstance().getApi().getSource(apiKey);
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                if (response.isSuccessful() && response.body().getSources() != null) {
                    msources.postValue(response.body().getSources());
                    madapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                //Toast.makeText(,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
