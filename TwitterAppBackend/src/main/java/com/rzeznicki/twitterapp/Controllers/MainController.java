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
    public MainController(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo, Environment environment) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
        mainService = new MainService(tweetRepo, keywordRepo, authorRepo, environment);
    }

    @GetMapping("/keywords")
    public List<KeywordDTO> getAllKeywords() {
        return keywordRepo.findAll().stream().map(mainService::convertToDto).toList();
    }

    @GetMapping("/tweets/{id}")
    public List<TweetDTO> getAllTweetsById(@PathVariable String id) {
        return tweetRepo.findAllByKeywordIdAndDeletedFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(mainService::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/keywords")
    public List<KeywordDTO> createKeyword(@RequestBody String keywordString) throws Exception {
        return mainService.getTweetsWithKeyword(keywordString).stream().map(mainService::convertToDto).toList();
    }

    @Transactional
    @PostMapping("/keyword/delete")
    public ResponseEntity deleteKeyword(@RequestBody String id) {
        tweetRepo.deleteAllByKeywordId(Long.valueOf(id));
        keywordRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tweetsByAuthorName/{name}")
    public List<TweetDTO> getAllTweetsByAuthorName(@PathVariable String name) {
        return tweetRepo.findAllByAuthorUserNameAndDeletedFalseOrderByCreatedAtDesc(name).stream().map(mainService::convertToDto).toList();
    }

    @Transactional
    @PostMapping("/deleteTweet")
    public ResponseEntity deleteTweetById(@RequestBody String id) {
        Tweet tweet = tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(true);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addAuthorToFavourite")
    public AuthorDTO addAuthorToFavourite(@RequestBody String id) throws Exception {
        Author author = authorRepo.findById(Long.valueOf(id)).get();
        author.setFavourite(true);
        mainService.getTweetsByAuthorId(id);
        return mainService.convertToDto(authorRepo.save(author));
    }

    @GetMapping("/favouriteAuthors")
    public List<AuthorDTO> getFavouriteAuthors() {
        return authorRepo.findAuthorByFavouriteTrue().stream().map(mainService::convertToDto).toList();
    }

    @Transactional
    @PostMapping("/favouriteAuthor/delete")
    public ResponseEntity deleteFavouriteAuthor(@RequestBody String id) {
        Author author = authorRepo.findById(Long.valueOf(id)).get();
        tweetRepo.deleteAllByAuthor_IdAndAuthorFavourite(author.getId(), true);
        author.setFavourite(false);
        authorRepo.save(author);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favouriteAuthor/{id}")
    public List<TweetDTO> getFavouriteAuthors(@PathVariable String id) {
        return tweetRepo.findAllByAuthorIdAndDeletedIsFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(mainService::convertToDto).toList();
    }

    @GetMapping("/trashTweets")
    public List<TweetDTO> getTrashTweets() {
        return tweetRepo.findAllByDeletedIsTrueOrderByCreatedAtDesc().stream().map(mainService::convertToDto).toList();
    }

    @PostMapping("/deleteTweetPermment")
    public ResponseEntity deleteTweetPermament(@RequestBody String id) {
        tweetRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/revertDeletedTweet")
    public ResponseEntity revertDeletedTweet(@RequestBody String id) {
        Tweet tweet = tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(false);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("reverted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refleshData")
    public void refleshDatabase() throws Exception {
        Iterable<Keyword> keywordList = keywordRepo.findAll();
        mainService.refleshDatabaseImp((List<Keyword>) keywordList);
    }

}
