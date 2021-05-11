package com.example.topnews

import com.example.topnews.interfces.AsyncNewsListener
import com.example.topnews.models.NewsArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoroutinesTask(val source: String, val articles: List<NewsArticles>, val asyncNewsListener: AsyncNewsListener) {

    fun postExecute(){
        GlobalScope.launch {
            asyncNewsListener.onDbOperationComplete()
        }
    }

    fun doInBackGround(){
        GlobalScope.launch {
            for (art: NewsArticles in articles) {
                art.newsId = source
                DatabaseHelper.getInstance().insertData(art)
            }
        }
    }

    fun execute(){
        GlobalScope.launch(Dispatchers.Default) {
            doInBackGround()
            postExecute()
        }
    }
}

