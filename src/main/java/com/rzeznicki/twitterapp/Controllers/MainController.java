package com.rzeznicki.twitterapp.Controllers;

import com.rzeznicki.twitterapp.Entities.*;
import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public MainController(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
    }
    ModelMapper modelMapper=new ModelMapper();

    private Keyword getTweetsWithKeyword(String keyword) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String pattern="yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        headers.set("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOcaWAEAAAAA6xSmtCZ1h%2BsBLvkP9QnAXYnoda4%3DaW649bjaaReyDvln0IC2eSnajPivrk3CPnt8xzD1XcUhXTnNqw");
        HttpEntity entity = new HttpEntity(headers);
        int lengthTweet = 0;
        Keyword keyword1=null;
        List<String> keywords= Arrays.stream(keyword.split("!%")).filter(StringUtils::isNotBlank).map(String::trim).toList();
        for(String keyword2: keywords) {
            ResponseEntity<String> response = rest.exchange(
                    "https://api.twitter.com/2/tweets/search/recent?query=(from:" + keyword2 + " OR " + keyword2 + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100",
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
                        authorRepo.save(new Author(Long.valueOf(jObj.getString("id")), jObj.getString("name"), jObj.getString("username"),false));
                    }
                }
                ja_data = jsonObj.getJSONArray("data");
                lengthTweet = ja_data.length();
                keyword1 = new Keyword(keyword2, lengthTweet, lengthTweet);
                int counterAdd=0;
                keywordRepo.save(keyword1);
                for (int i = 0; i < lengthTweet; i++) {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                        counterAdd++;
                        tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")), formatter.parse(jObj.getString("created_at")),
                                jObj.getString("text"), authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(), jObj.getString("lang"), keyword1,false));
                    }
                }
                keyword1.setTwitCounterLastSearch(counterAdd);
                keyword1.setTwitCounter(counterAdd);
                keyword1.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keyword2));
                keywordRepo.save(keyword1);
            } else {
                throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
            }
        }
        return keyword1;
    }

    @GetMapping("/refleshDatabase")
    public String refleshDatabase(Model model, HttpServletRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOcaWAEAAAAA6xSmtCZ1h%2BsBLvkP9QnAXYnoda4%3DaW649bjaaReyDvln0IC2eSnajPivrk3CPnt8xzD1XcUhXTnNqw");
        String pattern="yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        HttpEntity entity = new HttpEntity(headers);
        Iterable<Keyword> keywordList=keywordRepo.findAll();
        for(Keyword keyword: keywordList) {
            ResponseEntity<String> response = rest.exchange(
                    "https://api.twitter.com/2/tweets/search/recent?query=(from:" + keyword.getName() + " OR " + keyword.getName() + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100",
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
                        authorRepo.save(new Author(Long.valueOf(jObj.getString("id")), jObj.getString("name"), jObj.getString("username"),false));
                    }
                }
                ja_data = jsonObj.getJSONArray("data");
                length = ja_data.length();
                System.out.println(response.getBody());
                int counterAdd=0;
                for (int i = 0; i < length; i++) {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    if (tweetRepo.countTweetById(Long.valueOf(jObj.getString("id"))) == 0) {
                        counterAdd++;
                        tweetRepo.save(new Tweet(Long.valueOf(jObj.getString("id")), formatter.parse(jObj.getString("created_at")),
                                jObj.getString("text"), authorRepo.findById(Long.valueOf(jObj.getString("author_id"))).get(), jObj.getString("lang"), keywordRepo.findById(Long.valueOf("1")).get(),false));
                    }
                }
                keyword.setTwitCounterLastSearch(counterAdd);
                keyword.setTwitCounter(keyword.getTwitCounter()+counterAdd);
                keyword.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keyword.getName()));
                keywordRepo.save(keyword);
            } else {
                throw new Exception("Kod błedu z api: " + response.getStatusCode().value() + "error message:" + response.getBody());
            }
        }
       /* System.out.println(tweetList.getData().size());
        System.out.println();
        TweetV2.TweetData tweetData= tweetList.;
        System.out.printf(tweetData);
        Tweet*/
        System.out.println(keywordRepo.findAll().toString());
        return "index";
    }

    @GetMapping("/keywords")
    public List<KeywordDTO> getAllKeywords(){
        return keywordRepo.findAll().stream().map(this::convertToDto).toList();
    }

    @GetMapping("/tweets/{id}")
    public List<TweetDTO> getAllTweetsById(@PathVariable Long id){
        return tweetRepo.findAllByKeywordIdAndDeletedFalseOrderByCreatedAt(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/keywords")
    public KeywordDTO createEmployee(@RequestBody String keywordString) throws Exception {
        return convertToDto(getTweetsWithKeyword(keywordString));
    }

    @Transactional
    @PostMapping("/keyword/delete")
    public ResponseEntity deleteKeyword(@RequestBody Long id){
        tweetRepo.deleteAllByKeywordId(id);
        keywordRepo.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/tweetsByAuthorName/{name}")
    public List<TweetDTO> getAllTweetsByAuthorName(@PathVariable String name){
        return tweetRepo.findAllByAuthorUserName(name).stream().map(this::convertToDto).toList();
    }
    @Transactional
    @PostMapping("/deleteTweet")
    public ResponseEntity deleteTweetById(@RequestBody String id){
        tweetRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addAuthorToFavourite")
    public AuthorDTO addAuthorToFavourite(@RequestBody String id) {
        Author author= authorRepo.findById(Long.valueOf(id)).get();
        author.setFavourite(true);
        return convertToDto(authorRepo.save(author));
    }

    private TweetDTO convertToDto(Tweet tweet) {
        TweetDTO tweetDTO = modelMapper.map(tweet, TweetDTO.class);
        return tweetDTO;
    }
    private AuthorDTO convertToDto(Author author) {
        AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);
        return authorDTO;
    }
    private KeywordDTO convertToDto(Keyword keyword) {
        KeywordDTO keywordDTO = modelMapper.map(keyword, KeywordDTO.class);
        return keywordDTO;
    }

}
