package com.example.topnews.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topnews.ViewModel.NewsViewModel

public class NewsViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}