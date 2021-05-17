package com.example.topnews.models;

import com.example.newslibrary.Articles;
import java.util.List;


public class NewsHeadlines {

    private String status;
    private String totalResults;
    private List<NewsArticles> articles;

    public NewsHeadlines() {
    }

    public NewsHeadlines(String status, String totalResults, List<NewsArticles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsArticles> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticles> articles) {
        this.articles = articles;
    }
}
