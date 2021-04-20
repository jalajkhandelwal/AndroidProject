package com.example.topnews.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NewsSource extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;

    public NewsSource() {
    }

    public NewsSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
