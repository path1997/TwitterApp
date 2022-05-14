package com.rzeznicki.twitterapp.Entities;

import java.util.List;

public class AuthorDTO {
    private String id;
    private String name;
    private String userName;
    private List<TweetDTO> tweets;

    public AuthorDTO() {
    }

    public AuthorDTO(String id, String name, String userName, List<TweetDTO> tweets) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.tweets = tweets;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TweetDTO> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetDTO> tweets) {
        this.tweets = tweets;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", tweets=" + tweets +
                '}';
    }
}
