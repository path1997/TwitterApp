package com.rzeznicki.twitterapp;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterAppApplication.class, args);
    }

}
