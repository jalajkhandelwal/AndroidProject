package com.example.topnews;

import com.example.newslibrary.Articles;
import com.example.topnews.activities.NewsActivity;
import com.example.topnews.interfces.NewsIdListener;
import com.example.topnews.models.NewsArticles;
import com.example.topnews.models.NewsSource;
import com.example.topnews.models.NewsSources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper implements NewsIdListener{

    private Realm realm;
    private static RealmHelper instance;

    private RealmHelper(){
        realm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance(){
        if (instance == null){
            instance = new RealmHelper();
        }
        return instance;
    }

    public RealmHelper(Realm realm){
        this.realm = realm;
    }

    public void saveNews(NewsArticles articles){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
             Number max= realm.where(NewsArticles.class).max("id");
             if(max == null){
                 max = 1;
             } else {
                 max = max.intValue()+1;
             }
             articles.setId(max.intValue());
             realm.insertOrUpdate(articles);
            }
        });
    }

    public List<NewsArticles> readNews(){
        realm.beginTransaction();
        List<NewsArticles> articles1 = new ArrayList<>();
        RealmResults<NewsArticles> temp = realm.where(NewsArticles.class).findAll();
        articles1.addAll(temp);
        realm.commitTransaction();
        return articles1;
    }

    public void categorySaveNews(List<NewsSources> sources){
        realm.beginTransaction();
        RealmList<NewsSources> tempList = new RealmList<>();
        tempList.addAll(sources);
        realm.copyToRealmOrUpdate(tempList);
        realm.commitTransaction();
    }

    public List<NewsSources> categoryReadNews(){
        realm.beginTransaction();
        List<NewsSources> source1 = new ArrayList<>();
        RealmResults<NewsSources> temp = realm.where(NewsSources.class).equalTo("id","id").findAll();
        source1.addAll(temp);
        realm.commitTransaction();
        return source1;
    }

    @Override
    public void onNewsIdReceived(String id) {

    }
}
