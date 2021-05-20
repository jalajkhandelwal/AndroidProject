package com.example.topnews.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topnews.ViewModel.CategoryViewModel
import com.example.topnews.ViewModel.NewsViewModel

public class SourcesViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}