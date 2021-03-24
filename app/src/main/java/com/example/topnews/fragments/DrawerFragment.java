package com.example.topnews.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.Model.Source;
import com.example.topnews.Model.SourceResponse;
import com.example.topnews.Model.Sources;
import com.example.topnews.R;
import com.example.topnews.activities.MainActivity;
import com.example.topnews.adapters.CategoryRecyclerViewAdapter;
import com.example.topnews.adapters.NewsRecyclerViewAdapter;
import com.example.topnews.retrofit.ApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Sources> sources = new ArrayList<>();
    private CategoryRecyclerViewAdapter madapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String Key = "key";
    final String API_KEY = "14bfe05834a444b79739ac49197ae489";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer,null);
        recyclerView = view.findViewById(R.id.recy);
        layoutManager = new LinearLayoutManager(getActivity());
        madapter = new CategoryRecyclerViewAdapter(sources);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        //String country = getCountry();
        retrievejson(API_KEY);
        return view;
    }

    public void retrievejson(String apiKey){

        Call<SourceResponse> call = ApiClient.getInstance().getApi().getSource(apiKey);
        call.enqueue(new Callback<SourceResponse>() {

            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                if (response.isSuccessful() && response.body().getSources() != null) {
                    sources.clear();
                    sources = response.body().getSources();
                    madapter.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String json = gson.toJson(sources);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Key,json);
                    editor.apply();
                }
            }


            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String json = sp.getString(Key,"");
                sources.clear();
                List<Sources> art = new ArrayList<>();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Sources>>() {}.getType();
                art = gson.fromJson(json, type);
                sources.addAll(art);
                madapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
