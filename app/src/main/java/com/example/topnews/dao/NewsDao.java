package com.example.topnews.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsArticles> articles);

    @Query("SELECT * FROM articles_table")
    LiveData<List<NewsArticles>> getAllArticles();

    @Query("DELETE FROM articles_table")
    void deleteAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSources(List<NewsSources> sources);

    @Query("SELECT * FROM sources_table")
    LiveData<List<NewsSources>> getAllSources();

    @Query("DELETE FROM sources_table")
    void deleteAllSources();
}
