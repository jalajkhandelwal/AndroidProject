package com.example.topnews.dao;

import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsArticles> articles);

    @Query("SELECT * FROM articles_table where newsId= :news_Id")
    List<NewsArticles> getAllArticles(String news_Id);

    @Query("DELETE FROM articles_table")
    void deleteAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSources(List<NewsSources> sources);

    @Query("SELECT * FROM sources_table")
    LiveData<List<NewsSources>> getAllSources();

    @Query("DELETE FROM sources_table")
    void deleteAllSources();
}
