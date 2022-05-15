package com.rzeznicki.twitterapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @javax.persistence.Id
    @Id
    private Long id;
    private String name;
    private String userName;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Tweet> tweets;
    @JsonIgnore
    private boolean favourite;

    public Author() {
    }

    public Author(Long id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    public Author(Long id, String name, String userName, boolean favourite) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.favourite = favourite;
    }



    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", tweets=" + tweets +
                ", favourite=" + favourite +
                '}';
    }
}
