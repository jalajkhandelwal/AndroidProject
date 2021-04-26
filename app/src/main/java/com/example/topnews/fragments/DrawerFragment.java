package com.example.topnews.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.topnews.DatabaseHelper;
import com.example.topnews.R;
import com.example.topnews.ViewModel.CategoryViewModel;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.interfces.NewsIdListener;
import com.example.topnews.interfces.RecyclerClickListener;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrawerFragment extends Fragment implements RecyclerClickListener {

    private RecyclerView recyclerView;
    private List<NewsSources> sources = new ArrayList<>();
    private CategoryRecyclerViewAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    private NewsIdListener mNewsIdListener;
    private CategoryViewModel mCategoryViewModel;
    private LottieAnimationView lottieAnimationView;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initUI();
        initCatgViewModel();
        setCatgObserver();
        getNewsSources(AppConstants.API_KEY);
    }

    private void initUI() {

        recyclerView = getView().findViewById(R.id.recy);
        layoutManager = new LinearLayoutManager(getActivity());
        madapter = new CategoryRecyclerViewAdapter(sources,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        lottieAnimationView = new LottieAnimationView(getActivity());
        lottieAnimationView.findViewById(R.id.animationView);
    }

    private void initCatgViewModel() {
        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }

    private void setCatgObserver() {
        mCategoryViewModel.getCategoriesListObserver().observe(this, sources1 -> {
            Log.e("Drawer", "onCreate: observer" );
          //  List<NewsSources> mList  = RealmHelper.getInstance().categoryReadNews();
            List<NewsSources> mList = DatabaseHelper.getInstance().readSourcesData();
            sources.clear();
            sources.addAll(mList);
            madapter.notifyDataSetChanged();
            if(sources !=null && sources.size()>0){
                mNewsIdListener.onNewsIdReceived(sources.get(0).getId());
            }
        });

        mCategoryViewModel.getProgress().observe(this, showProgress -> {
            if(showProgress){
                lottieAnimationView.setMinAndMaxFrame(30,50);
            }else{
                lottieAnimationView.cancelAnimation();
            }
           // List<NewsSources> mList  = RealmHelper.getInstance().categoryReadNews();
            List<NewsSources> mList = DatabaseHelper.getInstance().readSourcesData();
            sources.clear();
            sources.addAll(mList);
            madapter.notifyDataSetChanged();
        });

        mCategoryViewModel.getSnackBar().observe(this, showSnackBar -> {
            Snackbar snackbar = Snackbar.make(getView(),"Error in Connection", Snackbar.LENGTH_SHORT);
            if(showSnackBar){
                snackbar.show();
            }else {
                snackbar.dismiss();
            }
        });

       mCategoryViewModel.getErrorLiveData().observe(this, showmessage -> {
           if(showmessage !=null) {
               materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
               materialAlertDialogBuilder.setTitle("Error");
               materialAlertDialogBuilder.setMessage("Cannot Load the News");
               materialAlertDialogBuilder.show();
             //  List<NewsSources> mList  = RealmHelper.getInstance().categoryReadNews();
               List<NewsSources> mList = DatabaseHelper.getInstance().readSourcesData();
               sources.clear();
               sources.addAll(mList);
               madapter.notifyDataSetChanged();
               if(sources !=null && sources.size()>0){
                   mNewsIdListener.onNewsIdReceived(sources.get(0).getId());
               }

           }
       });
    }

    public void getNewsSources(String apiKey) {
        mCategoryViewModel.getNewsSources(apiKey);
    }

    public void setNewsIdListener(NewsIdListener mNewsIdListener){
        this.mNewsIdListener = mNewsIdListener;
    }


    @Override
    public void onRecyclerClickListener(String id) {
        mNewsIdListener.onNewsIdReceived(id);
    }


}
