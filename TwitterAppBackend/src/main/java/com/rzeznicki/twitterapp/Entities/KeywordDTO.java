package com.rzeznicki.twitterapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;
import java.util.List;

public class KeywordDTO {
    private String id;
    private String name;
    private int twitCounter;
    private int twitCounterLastSearch;
    private boolean keywordIsAuthor;

    public KeywordDTO() {
    }

    public KeywordDTO(String id, String name, int twitCounter, int twitCounterLastSearch, boolean keywordIsAuthor) {
        this.id = id;
        this.name = name;
        this.twitCounter = twitCounter;
        this.twitCounterLastSearch = twitCounterLastSearch;
        this.keywordIsAuthor = keywordIsAuthor;
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

    public int getTwitCounter() {
        return twitCounter;
    }

    public void setTwitCounter(int twitCounter) {
        this.twitCounter = twitCounter;
    }

    public int getTwitCounterLastSearch() {
        return twitCounterLastSearch;
    }

    public void setTwitCounterLastSearch(int twitCounterLastSearch) {
        this.twitCounterLastSearch = twitCounterLastSearch;
    }

    public boolean isKeywordIsAuthor() {
        return keywordIsAuthor;
    }

    public void setKeywordIsAuthor(boolean keywordIsAuthor) {
        this.keywordIsAuthor = keywordIsAuthor;
    }

    @Override
    public String toString() {
        return "KeywordDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", twitCounter=" + twitCounter +
                ", twitCounterLastSearch=" + twitCounterLastSearch +
                ", keywordIsAuthor=" + keywordIsAuthor +
                '}';
    }
}
