package com.example.topnews.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.topnews.dao.NewsDao;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;

import org.jetbrains.annotations.NotNull;

@Database(entities = {NewsArticles.class, NewsSources.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="newsDatabase";

    public abstract NewsDao newsDao();
    private static  NewsDatabase instance;

    public static  NewsDatabase getInstance(Context context){
        if(instance == null){
            synchronized (NewsDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context,NewsDatabase.class,DATABASE_NAME)
                            .addTypeConverter(NewsSources.class)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void,Void,Void> {

        private NewsDao newsDao;

        PopulateAsyncTask(NewsDatabase newsDatabase){
            newsDao = newsDatabase.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAllArticles();
            newsDao.deleteAllSources();
            return null;
        }
    }
}
