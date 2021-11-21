package com.rzeznicki.twitterapp.Controllers;

import com.rzeznicki.twitterapp.Entities.Author;
import com.rzeznicki.twitterapp.Entities.Keyword;
import com.rzeznicki.twitterapp.Entities.Tweet;
import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.list.TwitterList;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.dto.tweet.TweetV2;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    private final TweetRepo tweetRepo;
    private final KeywordRepo keywordRepo;
    private final AuthorRepo authorRepo;

    @Autowired
    public MainController(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
    }

    @GetMapping("/refleshDatabase")
    public String refleshDatabase(Model model, HttpServletRequest request) throws Exception {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOcaWAEAAAAAJwR%2FQOSVa1xHNF6OWEsRhGj%2BKs0%3Dj9FoDFOXjQTtYBpJ5oAp98ONSL6Q5zvr2P83SFRPcR65yxZde4");
        HttpEntity entity = new HttpEntity(headers);
        Iterable<Keyword> keywordList=keywordRepo.findAll();
        for(Keyword keyword: keywordList) {
            ResponseEntity<String> response = rest.exchange(
                    "https://api.twitter.com/2/tweets/search/recent?query="+keyword.getName()+"&expansions=author_id&tweet.fields=created_at,lang,source",
                    HttpMethod.GET,
                    entity,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(response.getBody());
                JSONObject jsonObj = new JSONObject(response.getBody());
                JSONObject jsonObj1 = jsonObj.getJSONObject("includes");
                JSONArray ja_data = jsonObj1.getJSONArray("users");
                int length = ja_data.length();

                for (int i = 0; i < length; i++) {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    if (authorRepo.countAuthorById(Long.valueOf(jObj.getString("id"))) == 0) {
                        authorRepo.save(new Author(Long.valueOf(jObj.getString("id")), jObj.getString("name"), jObj.getString("username")));
                    }
                }
                ja_data = jsonObj.getJSONArray("data");
                length = ja_data.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                        tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")), jObj.getString("created_at"),
                                jObj.getString("text"), authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(), jObj.getString("lang"), keywordRepo.findById(Long.valueOf("1")).get()));
                    }
                }
            } else {
                throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
            }
        }
       /* System.out.println(tweetList.getData().size());
        System.out.println();
        TweetV2.TweetData tweetData= tweetList.;
        System.out.printf(tweetData);
        Tweet*/
        return "index";
    }
}
