package com.example.topnews.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.R;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.constants.AppConstants;
import com.example.topnews.interfces.NewsIdListener;
import com.example.topnews.interfces.RecyclerClickListener;
import com.example.topnews.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerFragment extends Fragment implements RecyclerClickListener {

    private RecyclerView recyclerView;
    private List<Sources> sources = new ArrayList<>();
    private CategoryRecyclerViewAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    private NewsIdListener mNewsIdListener;
    //private NewsViewModel newsViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.recy);
        layoutManager = new LinearLayoutManager(getActivity());
        madapter = new CategoryRecyclerViewAdapter(sources,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        getNewsSources(AppConstants.API_KEY);
    }

    public void setNewsIdListener(NewsIdListener mNewsIdListener){
        this.mNewsIdListener = mNewsIdListener;
    }

    public void getNewsSources(String apiKey){
        Call<SourceResponse> call = ApiClient.getInstance().getApi().getSource(apiKey);
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                if (response.isSuccessful() && response.body().getSources() != null) {
                    sources.clear();
                    sources.addAll(response.body().getSources());
                    madapter.notifyDataSetChanged();
                    Log.e("TAG", "onResponse: " + sources.size());
                    if(sources !=null && sources.size()>0){
                        mNewsIdListener.onNewsIdReceived(sources.get(0).getId()); //calling listener
                    }
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRecyclerClickListener(String id) {
        mNewsIdListener.onNewsIdReceived(id);
    }
}
