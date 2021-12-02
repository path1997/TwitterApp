package com.rzeznicki.twitterapp.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tweets")
public class Tweet {
    @javax.persistence.Id
    @Id
    private Long id;
    private Date createdAt;
    @Column(length = 10000)
    private String text;
    @JsonIgnoreProperties(value = {"tweets"})
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;
    private String lang;
    @JsonIgnoreProperties(value = {"tweets"})
    @ManyToOne
    @JoinColumn(name = "keywordId")
    private Keyword keyword;
    @JsonIgnore
    private boolean deleted;

    public Tweet() {
    }

    public Tweet(Long id, Date createdAt, String text, Author author, String lang, Keyword keyword) {
        this.id = id;
        this.createdAt = createdAt;
        this.text = text;
        this.author = author;
        this.lang = lang;
        this.keyword = keyword;
    }

    public Tweet(Long id, Date createdAt, String text, Author author, String lang, Keyword keyword, boolean deleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.text = text;
        this.author = author;
        this.lang = lang;
        this.keyword = keyword;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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
