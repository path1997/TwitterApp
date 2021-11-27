package com.rzeznicki.twitterapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "keywords")
public class Keyword {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int twitCounter;
    private int twitCounterLastSearch;
    @JsonIgnore
    @OneToMany(mappedBy = "keyword")
    private List<Tweet> tweets;

    public Keyword() {
    }

    public Keyword(String name, int twitCounter, int twitCounterLastSearch) {
        this.name = name;
        this.twitCounter = twitCounter;
        this.twitCounterLastSearch = twitCounterLastSearch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

}
