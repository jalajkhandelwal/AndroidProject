package com.example.topnews.models;

import com.google.gson.Gson;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

@ProvidedTypeConverter
public class SourceConverter {

    @TypeConverter
    public static NewsSource storedStringtoSource(String data){
        Gson gson = new Gson();
        return gson.fromJson(data, NewsSource.class);
    }

    @TypeConverter
    public static String SourceToString(NewsSource source){
        Gson gson = new Gson();
        return gson.toJson(source);
    }
}
