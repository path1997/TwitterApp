package com.rzeznicki.twitterapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TweetDTO {
    private String id;
    private String createdAt;
    private String text;
    @JsonIgnoreProperties(value = {"tweets"})
    private AuthorDTO author;
    private String lang;
    @JsonIgnoreProperties(value = {"tweets"})
    private KeywordDTO keyword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return this.createdAt.toString().substring(0,10)+" "+this.createdAt.toString().substring(11,19);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public KeywordDTO getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordDTO keyword) {
        this.keyword = keyword;
    }
}