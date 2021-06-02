package com.example.topnews.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newslibrary.Sources;
import com.example.topnews.models.NewsSources;
import com.example.topnews.repositories.CategoryActivityRepository;
import com.example.topnews.repositories.DbRepo;
import com.example.topnews.repositories.NewsActivityRepository;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.InvocationTargetException;
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
    private DbRepo dbRepo;
    private LiveData<List<NewsSources>> getAllSources;

    public CategoryViewModel() {
        mCategoryLiveData = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        snackBar = new MutableLiveData<>();
        mErrorLiveData = new MutableLiveData<>();
        mCatgRepository = CategoryActivityRepository.getInstance();
    }

    public void initRepo(Application application){
        dbRepo = new DbRepo(application);
        getAllSources = dbRepo.getAllSources();
    }

    public void insert(List<NewsSources> sourcesList){
        dbRepo.insertSources(sourcesList);
    }

    public LiveData<List<NewsSources>> getAllSources(){
        return getAllSources;
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

    public void getNewsSources(String apiKey) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mCatgRepository.setLoader(progress);
        mCatgRepository.newsSources(mCategoryLiveData,mErrorLiveData);
    }

}
