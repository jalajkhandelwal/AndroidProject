package com.example.topnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.topnews.Model.Articles;
import com.example.topnews.Model.Headlines;
import com.example.topnews.Model.Source;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    final String API_KEY = "14bfe05834a444b79739ac49197ae489";
    private Adapter adapter;
    private List<Articles> articles = new ArrayList<>();
    //Context context;
    private static final String Key = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolb);
        //setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(articles);
        recyclerView.setAdapter(adapter);
        String country = getCountry();
        retrievejson(country,API_KEY);

    }


    public void retrievejson(String country, String apiKey){

        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {

            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter.notifyDataSetChanged();
                     Gson gson = new Gson();
                     String json = gson.toJson(articles);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                     SharedPreferences.Editor editor = sp.edit();
                     editor.putString(Key,json);
                     editor.apply();
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String json = sp.getString(Key,"");
                articles.clear();
                List<Articles> art = new ArrayList<>();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Articles>>() {}.getType();
                art = gson.fromJson(json, type);
                articles.addAll(art);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}