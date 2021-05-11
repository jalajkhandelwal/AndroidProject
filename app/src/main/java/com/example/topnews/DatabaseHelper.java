package com.example.topnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSources;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String dbname = "news.db";

    private static DatabaseHelper mHelper;

    private DatabaseHelper(Context context) {
        super(context,dbname,null,1);
    }

    public static void initDataBase(Context context){
        mHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance(){
        if (mHelper != null){
            return mHelper;
        }
        throw  new RuntimeException("DataBase not initialised");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("newsSources", "onCreate: ");

        String qry = "create table newsArticles(id int, newsId text,source text, author text, title text, description text, url text, urlToImage text, publishedAt text, image blob)";
        String qry2 = "create table newsSources(id text, name text, description text, url text, category text,language text, country text)";
        sqLiteDatabase.execSQL(qry);
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS newsArticles");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABlE IF EXISTS newsSources");
        onCreate(sqLiteDatabase);
    }

    public void insertData(NewsArticles articles){
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",articles.getId());
        values.put("newsId",articles.getNewsId());
        values.put("source",articles.getSource().toString());
        values.put("author",articles.getAuthor());
        values.put("title",articles.getTitle());
        values.put("description",articles.getDescription());
        values.put("url",articles.getUrl());
        values.put("urlToImage",articles.getUrlToImage());
        values.put("publishedAt",articles.getPublishedAt());
        Log.e("DBhelper", "insertData: ");
        sqLiteDatabase.insert("newsArticles",null,values);
    }


    public List<NewsArticles> readData(String newsId){
        Log.e("TAG", "readData: in readData" );
        List<NewsArticles> articles = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from newsArticles where newsId='"+newsId+"'",null);
        res.moveToFirst();

        while (res.moveToNext()){
            Log.e("Helper", "readData: " );
           NewsArticles articles1 = new NewsArticles();
           articles1.setId(res.getInt(0));
           articles1.setNewsId(res.getString(1));
           articles1.setAuthor(res.getString(2));
           articles1.setTitle(res.getString(3));
           articles1.setDescription(res.getString(4));
           articles1.setUrl(res.getString(5));
           //byte[] blob = res.getBlob(Integer.parseInt(res.getString(6)));
           articles1.setUrlToImage(res.getString(6));
           //articles1.setUrlToImage(getBytes(getImage(blob)));
           articles1.setPublishedAt(res.getString(7));
           articles.add(articles1);
        }
        res.close();
        Log.e("DatabaseHelper", "readData: " + articles);
        return articles;
    }

    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    private Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }

     public void insertSourcesData(NewsSources sources){
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",sources.getId());
        values.put("name",sources.getName());
        // Log.e("vlaues", "insertSourcesData: "+values);
        values.put("description",sources.getDescription());
        values.put("url",sources.getUrl());
        values.put("category",sources.getCategory());
        values.put("language",sources.getLanguage());
        values.put("country",sources.getCountry());
        Log.e("DBhelper", "insertSourcesData: ");
        sqLiteDatabase.insert("newsSources",null,values);
    }

     public List<NewsSources> readSourcesData(){
        Log.e("TAG", "readData: in readData" );
        List<NewsSources> sources = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from newsSources",null);
        res.moveToFirst();

        while (res.moveToNext()){
            Log.e("Helper", "readSourcesData: " );
            NewsSources sources1 = new NewsSources();
            sources1.setId(res.getString(0));
            sources1.setName(res.getString(1));
            sources1.setDescription(res.getString(2));
            sources1.setUrl(res.getString(3));
            sources1.setCategory(res.getString(4));
            sources1.setLanguage(res.getString(5));
            sources1.setCountry(res.getString(6));
            sources.add(sources1);
        }
        res.close();
        Log.e("DatabaseHelper", "readNewsData: " + sources);
        return sources;
    }

}
