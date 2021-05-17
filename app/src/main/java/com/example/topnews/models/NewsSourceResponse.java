package com.example.topnews.models;

import com.example.newslibrary.Sources;
import java.util.List;

public class NewsSourceResponse {

    private String status;
    private List<NewsSources> sources;

    public NewsSourceResponse() {
    }

    public NewsSourceResponse(String status, List<NewsSources> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsSources> getSources() {
        return sources;
    }

    public void setSources(List<NewsSources> sources) {
        this.sources = sources;
    }
}
