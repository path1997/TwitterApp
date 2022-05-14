package com.rzeznicki.twitterapp.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzeznicki.twitterapp.Entities.*;
import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
import com.rzeznicki.twitterapp.Services.MainService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/")
public class MainController {
    private final TweetRepo tweetRepo;
    private final KeywordRepo keywordRepo;
    private final AuthorRepo authorRepo;
    private MainService mainService;

    @Autowired
    public MainController(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
        mainService = new MainService(tweetRepo, keywordRepo, authorRepo);
    }

    @GetMapping("/keywords")
    public List<KeywordDTO> getAllKeywords() {
        return mainService.getAllKeywords();
    }

    @GetMapping("/tweets/{id}")
    public List<TweetDTO> getAllTweetsById(@PathVariable String id) {
        return mainService.getAllTweetsById(id);
    }

    @PostMapping("/keywords")
    public List<KeywordDTO> createKeyword(@RequestBody String keywordString) throws Exception {
        return mainService.createKeyword(keywordString);
    }

    @Transactional
    @PostMapping("/keyword/delete")
    public ResponseEntity deleteKeyword(@RequestBody String id) {
        return mainService.deleteKeyword(id);
    }

    @GetMapping("/tweetsByAuthorName/{name}")
    public List<TweetDTO> getAllTweetsByAuthorName(@PathVariable String name) {
        return mainService.getAllTweetsByAuthorName(name);
    }

    @Transactional
    @PostMapping("/deleteTweet")
    public ResponseEntity deleteTweetById(@RequestBody String id) {
        return mainService.deleteTweetById(id);
    }

    @PostMapping("/addAuthorToFavourite")
    public AuthorDTO addAuthorToFavourite(@RequestBody String id) throws Exception {
        return mainService.addAuthorToFavourite(id);
    }

    @GetMapping("/favouriteAuthors")
    public List<AuthorDTO> getFavouriteAuthors() {
        return mainService.getFavouriteAuthors();
    }

    @Transactional
    @PostMapping("/favouriteAuthor/delete")
    public ResponseEntity deleteFavouriteAuthor(@RequestBody String id) {
        return mainService.deleteFavouriteAuthor(id);
    }

    @GetMapping("/favouriteAuthor/{id}")
    public List<TweetDTO> getFavouriteAuthors(@PathVariable String id) {
       return mainService.getFavouriteAuthors(id);
    }

    @GetMapping("/trashTweets")
    public List<TweetDTO> getTrashTweets() {
       return mainService.getTrashTweets();
    }

    @PostMapping("/deleteTweetPermment")
    public ResponseEntity deleteTweetPermament(@RequestBody String id) {
        return mainService.deleteTweetPermament(id);
    }

    @PostMapping("/revertDeletedTweet")
    public ResponseEntity revertDeletedTweet(@RequestBody String id) {
        return mainService.revertDeletedTweet(id);
    }

    @GetMapping("/refleshData")
    public void refleshDatabase() throws Exception {
        mainService.refleshDatabase();
    }

}
