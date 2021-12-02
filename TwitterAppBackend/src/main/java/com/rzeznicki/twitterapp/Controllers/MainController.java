package com.rzeznicki.twitterapp.Controllers;

import com.rzeznicki.twitterapp.Entities.*;
import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
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
import org.springframework.web.client.RestTemplate;
import twitter4j.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class MainController {
    private final RestTemplate rest = new RestTemplate();
    private final TweetRepo tweetRepo;
    private final KeywordRepo keywordRepo;
    private final AuthorRepo authorRepo;
    private HttpHeaders headers = new HttpHeaders();
    private String pattern="yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
    private SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    private HttpEntity entity;


    @Autowired
    public MainController(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo, Environment environment) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
        this.headers.set("Authorization", "Bearer " + environment.getProperty("baererKey"));
        entity = new HttpEntity(headers);
    }
    ModelMapper modelMapper=new ModelMapper();

    @GetMapping("/keywords")
    public List<KeywordDTO> getAllKeywords(){
        return keywordRepo.findAll().stream().map(this::convertToDto).toList();
    }

    @GetMapping("/tweets/{id}")
    public List<TweetDTO> getAllTweetsById(@PathVariable String id){
        return tweetRepo.findAllByKeywordIdAndDeletedFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/keywords")
    public List<KeywordDTO> createKeyword(@RequestBody String keywordString) throws Exception {
        return getTweetsWithKeyword(keywordString).stream().map(this::convertToDto).toList();
    }

    @Transactional
    @PostMapping("/keyword/delete")
    public ResponseEntity deleteKeyword(@RequestBody String id){
        tweetRepo.deleteAllByKeywordId(Long.valueOf(id));
        keywordRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/tweetsByAuthorName/{name}")
    public List<TweetDTO> getAllTweetsByAuthorName(@PathVariable String name){
        return tweetRepo.findAllByAuthorUserNameAndDeletedFalseOrderByCreatedAtDesc(name).stream().map(this::convertToDto).toList();
    }
    @Transactional
    @PostMapping("/deleteTweet")
    public ResponseEntity deleteTweetById(@RequestBody String id){
        Tweet tweet = tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(true);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addAuthorToFavourite")
    public AuthorDTO addAuthorToFavourite(@RequestBody String id) throws Exception {
        Author author= authorRepo.findById(Long.valueOf(id)).get();
        author.setFavourite(true);
        getTweetsByAuthorId(id);
        return convertToDto(authorRepo.save(author));
    }

    @GetMapping("/favouriteAuthors")
    public List<AuthorDTO> getFavouriteAuthors(){
        return authorRepo.findAuthorByFavouriteTrue().stream().map(this::convertToDto).toList();
    }

    @Transactional
    @PostMapping("/favouriteAuthor/delete")
    public ResponseEntity deleteFavouriteAuthor(@RequestBody String id){
        Author author=authorRepo.findById(Long.valueOf(id)).get();
        tweetRepo.deleteAllByAuthor_IdAndAuthorFavourite(author.getId(),true);
        author.setFavourite(false);
        authorRepo.save(author);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favouriteAuthor/{id}")
    public List<TweetDTO> getFavouriteAuthors(@PathVariable String id){
        return tweetRepo.findAllByAuthorIdAndDeletedIsFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(this::convertToDto).toList();
    }

    @GetMapping("/trashTweets")
    public List<TweetDTO> getTrashTweets(){
        return tweetRepo.findAllByDeletedIsTrueOrderByCreatedAtDesc().stream().map(this::convertToDto).toList();
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
        Tweet tweet=tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(false);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("reverted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refleshData")
    public void refleshDatabase() throws Exception {
        Iterable<Keyword> keywordList=keywordRepo.findAll();
        for(Keyword keyword: keywordList) {
            ResponseEntity<String> response = rest.exchange(
                    "https://api.twitter.com/2/tweets/search/recent?query=(from:" + keyword.getName() + " OR " + keyword.getName() + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100",
                    HttpMethod.GET,
                    entity,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonObj = new JSONObject(response.getBody());
                JSONObject jsonObjIncludes = jsonObj.getJSONObject("includes");
                JSONArray jsonArrayUsers = jsonObjIncludes.getJSONArray("users");
                for (int i = 0; i < jsonArrayUsers.length(); i++) {
                    JSONObject jObj = jsonArrayUsers.getJSONObject(i);
                    if (authorRepo.countAuthorById(Long.valueOf(jObj.getString("id"))) == 0) {
                        authorRepo.save(new Author(Long.valueOf(jObj.getString("id")), jObj.getString("name"), jObj.getString("username"),false));
                    }
                }
                JSONArray jsonArrayData = jsonObj.getJSONArray("data");
                int counterAdd=0;
                for (int i = 0; i < jsonArrayData.length(); i++) {
                    JSONObject jObj = jsonArrayData.getJSONObject(i);
                    if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                        counterAdd++;
                        tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")),
                                formatter.parse(jObj.getString("created_at")),
                                jObj.getString("text"),
                                authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(),
                                jObj.getString("lang"), keywordRepo.findById(keyword.getId()).get(),
                                false));
                    }
                }
                keyword.setTwitCounterLastSearch(counterAdd);
                keyword.setTwitCounter(keyword.getTwitCounter()+counterAdd);
                keyword.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keyword.getName()));
                keywordRepo.save(keyword);
            } else {
                throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
            }
            for(Author author: authorRepo.findAuthorByFavouriteTrue()){
                getTweetsByAuthorId(String.valueOf(author.getId()));
            }
        }
    }

    private TweetDTO convertToDto(Tweet tweet) {
        return modelMapper.map(tweet, TweetDTO.class);
    }
    private AuthorDTO convertToDto(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }
    private KeywordDTO convertToDto(Keyword keyword) {
        return modelMapper.map(keyword, KeywordDTO.class);
    }

    private List<Keyword> getTweetsWithKeyword(String keyword) throws Exception {
        Keyword keywordToSave;
        List<Keyword> keywordsToReturn=new ArrayList<>();
        List<String> keywords= Arrays.stream(keyword.split("!%")).filter(StringUtils::isNotBlank).map(String::trim).toList();
        for(String keywordFromString: keywords) {
            ResponseEntity<String> response = rest.exchange(
                    "https://api.twitter.com/2/tweets/search/recent?query=(from:" + keywordFromString + " OR " + keywordFromString + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100",
                    HttpMethod.GET,
                    entity,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonObj = new JSONObject(response.getBody());
                JSONObject jsonObjIncludes = jsonObj.getJSONObject("includes");
                JSONArray jsonArrayUsers = jsonObjIncludes.getJSONArray("users");
                for (int i = 0; i < jsonArrayUsers.length(); i++) {
                    JSONObject jObj = jsonArrayUsers.getJSONObject(i);
                    if (authorRepo.countAuthorById(Long.valueOf(jObj.getString("id"))) == 0) {
                        authorRepo.save(new Author(Long.valueOf(jObj.getString("id")), jObj.getString("name"), jObj.getString("username"),false));
                    }
                }
                JSONArray jsonArrayData = jsonObj.getJSONArray("data");
                int lengthTweet = jsonArrayData.length();
                keywordToSave = new Keyword(keywordFromString, lengthTweet, lengthTweet);
                int counterAdd=0;
                keywordRepo.save(keywordToSave);
                for (int i = 0; i < lengthTweet; i++) {
                    JSONObject jObj = jsonArrayData.getJSONObject(i);
                    if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                        counterAdd++;
                        tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")),
                                formatter.parse(jObj.getString("created_at")),
                                jObj.getString("text"),
                                authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(),
                                jObj.getString("lang"),
                                keywordToSave,
                                false));
                    }
                }
                keywordToSave.setTwitCounterLastSearch(counterAdd);
                keywordToSave.setTwitCounter(counterAdd);
                keywordToSave.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keywordFromString));
                keywordRepo.save(keywordToSave);
                keywordsToReturn.add(keywordToSave);
            } else {
                throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
            }
        }
        return keywordsToReturn;
    }

    private void getTweetsByAuthorId(String authorId) throws Exception {
        ResponseEntity<String> response = rest.exchange(
                "https://api.twitter.com/2/tweets/search/recent?query=from:" + authorId + " (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100",
                HttpMethod.GET,
                entity,
                String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            JSONArray jsonArrayData = jsonObj.getJSONArray("data");
            int lengthTweet = jsonArrayData.length();
            for (int i = 0; i < lengthTweet; i++) {
                JSONObject jObj = jsonArrayData.getJSONObject(i);
                if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                    tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")),
                            formatter.parse(jObj.getString("created_at")),
                            jObj.getString("text"),
                            authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(),
                            jObj.getString("lang"),
                            null,
                            false));
                }
            }
        } else {
            throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
        }
    }

}
