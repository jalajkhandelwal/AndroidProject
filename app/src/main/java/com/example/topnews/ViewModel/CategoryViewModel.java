package com.example.topnews.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.repositories.CategoryActivityRepository;
import com.example.topnews.repositories.NewsActivityRepository;
import com.example.topnews.retrofit.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<Sources>> mCategoryLiveData;
    private MutableLiveData<String> mErrorLiveData;
    private MutableLiveData<Boolean> progress;
    private MutableLiveData<Boolean> snackBar;
    private CategoryActivityRepository mCatgRepository;


    public CategoryViewModel() {
        mCategoryLiveData = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        snackBar = new MutableLiveData<>();
        mErrorLiveData = new MutableLiveData<>();
        mCatgRepository = CategoryActivityRepository.getInstance();
    }

    public MutableLiveData<List<Sources>> getCategoriesListObserver() {
        return mCategoryLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return mErrorLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    public MutableLiveData<Boolean> getSnackBar() {
        return snackBar;
    }

    public void getNewsSources(String apiKey){
        mCatgRepository.setLoader(progress);
        mCatgRepository.getNewsSources(apiKey,mCategoryLiveData,mErrorLiveData);
    }

}
