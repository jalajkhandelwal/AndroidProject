package com.example.topnews.models;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SourceConverter {

    @TypeConverter
    public static List<NewsSource> storedStringtoSource(String data){
        Gson gson = new Gson();
        if(data == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<NewsSource>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String SourceToString(List<NewsSource> source){
        Gson gson = new Gson();
        return gson.toJson(source);
    }
}
