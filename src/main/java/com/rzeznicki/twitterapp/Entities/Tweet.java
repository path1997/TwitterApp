package com.rzeznicki.twitterapp.Entities;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "tweets")
public class Tweet {
    @javax.persistence.Id
    @Id
    private Long id;
    private String createdAt;
    private String text;
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;
    private String lang;
    @ManyToOne
    @JoinColumn(name = "keywordId")
    private Keyword keyword;

    public Tweet() {
    }

    public Tweet(Long id, String createdAt, String text, Author author, String lang, Keyword keyword) {
        this.id = id;
        this.createdAt = createdAt;
        this.text = text;
        this.author = author;
        this.lang = lang;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
