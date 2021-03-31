package com.example.topnews.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.retrofit.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<Sources>> mCategoryLiveData;
    private MutableLiveData<Boolean> progress;
    private MutableLiveData<Boolean> snackBar;


    public CategoryViewModel() {
        mCategoryLiveData = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        snackBar = new MutableLiveData<>();
    }

    public MutableLiveData<List<Sources>> getCategoriesListObserver() {
        return mCategoryLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    public MutableLiveData<Boolean> getSnackBar() {
        return snackBar;
    }

    public void getNewsSources(String apiKey){
        progress.postValue(true);
        Call<SourceResponse> call = ApiClient.getInstance().getApi().getSource(apiKey);
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                progress.postValue(false);
                if (response.isSuccessful() && response.body().getSources() != null) {
                   mCategoryLiveData.postValue(response.body().getSources());
                    //Log.e("TAG", "onResponse: " + mCategoryLiveData.size());
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                progress.postValue(false);
                snackBar.postValue(true);
            }
        });
    }

}
