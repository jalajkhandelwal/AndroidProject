package com.example.topnews.retrofit;

import com.example.topnews.Model.Headlines;
import com.example.topnews.Model.Source;
import com.example.topnews.Model.SourceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
          @Query("country") String country,
          @Query("apiKey") String apiKey
    );

    @GET("sources")
    Call<SourceResponse> getSource(
            @Query("apiKey") String apiKey
    );

}
