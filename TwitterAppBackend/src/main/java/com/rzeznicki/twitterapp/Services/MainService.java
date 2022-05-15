package com.rzeznicki.twitterapp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainService {
    public final RestTemplate rest;
    public final TweetRepo tweetRepo;
    public final KeywordRepo keywordRepo;
    public final AuthorRepo authorRepo;
    public HttpHeaders headers = new HttpHeaders();
    public String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'";
    public SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    public HttpEntity entity;
    public String urlToTwitterApi = "https://api.twitter.com/2/tweets/search/recent?query=";
    public ObjectMapper objectMapper;
    public ModelMapper modelMapper;

    public MainService(TweetRepo tweetRepo, KeywordRepo keywordRepo, AuthorRepo authorRepo) {
        this.tweetRepo = tweetRepo;
        this.keywordRepo = keywordRepo;
        this.authorRepo = authorRepo;
        this.headers.set("Authorization", "Bearer " + System.getenv("baererKey"));
        entity = new HttpEntity(headers);
        objectMapper = new ObjectMapper();
        modelMapper = new ModelMapper();
        rest = new RestTemplate();
    }

    public List<KeywordDTO> getAllKeywords(){
        return keywordRepo.findAll().stream().map(this::convertToDto).toList();
    }

    public List<TweetDTO> getAllTweetsById(String id){
        return tweetRepo.findAllByKeywordIdAndDeletedFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<KeywordDTO> createKeyword(String keywordString) throws Exception {
        return getTweetsWithKeyword(keywordString).stream().map(this::convertToDto).toList();
    }

    public ResponseEntity deleteKeyword(String id){
        tweetRepo.deleteAllByKeywordId(Long.valueOf(id));
        keywordRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public List<TweetDTO> getAllTweetsByAuthorName(String name) {
        return tweetRepo.findAllByAuthorUserNameAndDeletedFalseOrderByCreatedAtDesc(name).stream().map(this::convertToDto).toList();
    }

    public ResponseEntity deleteTweetById(String id) {
        Tweet tweet = tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(true);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public AuthorDTO addAuthorToFavourite(String id) throws Exception {
        Author author = authorRepo.findById(Long.valueOf(id)).get();
        author.setFavourite(true);
        getTweetsByAuthorId(id);
        return convertToDto(authorRepo.save(author));
    }

    public List<AuthorDTO> getFavouriteAuthors() {
        return authorRepo.findAuthorByFavouriteTrue().stream().map(this::convertToDto).toList();
    }

    public ResponseEntity deleteFavouriteAuthor(String id) {
        Author author = authorRepo.findById(Long.valueOf(id)).get();
        tweetRepo.deleteAllByAuthor_IdAndAuthorFavourite(author.getId(), true);
        author.setFavourite(false);
        authorRepo.save(author);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public List<TweetDTO> getFavouriteAuthors(String id) {
        return tweetRepo.findAllByAuthorIdAndDeletedIsFalseOrderByCreatedAtDesc(Long.valueOf(id)).stream().map(this::convertToDto).toList();
    }

    public List<TweetDTO> getTrashTweets() {
        return tweetRepo.findAllByDeletedIsTrueOrderByCreatedAtDesc().stream().map(this::convertToDto).toList();
    }

    public ResponseEntity deleteTweetPermament(String id) {
        tweetRepo.deleteById(Long.valueOf(id));
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity revertDeletedTweet(String id) {
        Tweet tweet = tweetRepo.findById(Long.valueOf(id)).get();
        tweet.setDeleted(false);
        tweetRepo.save(tweet);
        Map<String, Boolean> response = new HashMap<>();
        response.put("reverted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public void refleshDatabase() throws Exception {
        Iterable<Keyword> keywordList = keywordRepo.findAll();
        refleshDatabaseImp((List<Keyword>) keywordList);
    }

    public TweetDTO convertToDto(Tweet tweet) {
        return modelMapper.map(tweet, TweetDTO.class);
    }

    public AuthorDTO convertToDto(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }

    public KeywordDTO convertToDto(Keyword keyword) {
        return modelMapper.map(keyword, KeywordDTO.class);
    }

    public String performRequestToTwitterApi(String query) throws Exception {
        ResponseEntity<String> response = performRequestImpl(urlToTwitterApi+query);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Error code: " + response.getStatusCode().value() + " error message:" + response.getBody());
        }
        if (StringUtils.isBlank(response.getBody())) {
            throw new Exception("Empty response");
        }
        System.out.println(response.getBody());
        return response.getBody();
    }

    public ResponseEntity<String> performRequestImpl(String url){
        return rest.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);
    }

    public List<Keyword> getTweetsWithKeyword(String keyword) throws Exception {
        Keyword keywordToSave;
        List<Keyword> keywordsToReturn = new ArrayList<>();
        List<String> keywords = Arrays.stream(keyword.split("!%")).filter(StringUtils::isNotBlank).map(String::trim).toList();
        for (String keywordFromString : keywords) {
            String query = "(from:" + keywordFromString + " OR " + keywordFromString + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100";
            ResponseTweets responseTweets = objectMapper.readValue(performRequestToTwitterApi(query), ResponseTweets.class);
            for (User user : responseTweets.getIncludes().getUsers()) {
                if (authorRepo.countAuthorById(Long.valueOf(user.getId())) == 0) {
                    authorRepo.save(new Author(Long.parseLong(user.getId()), user.getName(), user.getUsername(), false));
                }
            }

            int lengthTweet = responseTweets.getData().size();
            keywordToSave = new Keyword(keywordFromString, lengthTweet, lengthTweet);
            int counterAdd = 0;
            keywordRepo.save(keywordToSave);

            for (Datum data : responseTweets.getData()) {
                if (tweetRepo.countTweetById(Long.parseLong(data.getId())) == 0) {
                    counterAdd++;
                    tweetRepo.save(new Tweet(Long.valueOf(data.getId()),
                            formatter.parse(data.getCreatedAt()),
                            data.getText(),
                            authorRepo.findById(Long.valueOf(data.getAuthorId())).get(),
                            data.getLang(),
                            keywordToSave,
                            false));
                }
            }
            keywordToSave.setTwitCounterLastSearch(counterAdd);
            keywordToSave.setTwitCounter(counterAdd);
            keywordToSave.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keywordFromString));
            keywordRepo.save(keywordToSave);
            keywordsToReturn.add(keywordToSave);
        }
        return keywordsToReturn;
    }

    public void getTweetsByAuthorId(String authorId) throws Exception {
        String query = "from:" + authorId + " (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100";
        ResponseTweets responseTweets = objectMapper.readValue(performRequestToTwitterApi(query), ResponseTweets.class);
        for (Datum data : responseTweets.getData()) {
            if (tweetRepo.countTweetById(Long.valueOf(data.getId())) == 0) {
                tweetRepo.save(new Tweet(Long.valueOf(data.getId()),
                        formatter.parse(data.getCreatedAt()),
                        data.getText(),
                        authorRepo.findById(Long.valueOf(data.getAuthorId())).get(),
                        data.getLang(),
                        null,
                        false));
            }
        }
    }

    public void refleshDatabaseImp(List<Keyword> keywords) throws Exception {
        for (Keyword keyword : keywords) {
            String query = "(from:" + keyword.getName() + " OR " + keyword.getName() + ") (lang:en OR lang:pl)&expansions=author_id&tweet.fields=created_at,lang,source&max_results=100";
            ResponseTweets responseTweets = objectMapper.readValue(performRequestToTwitterApi(query), ResponseTweets.class);
            for (User user : responseTweets.getIncludes().getUsers()) {
                if (authorRepo.countAuthorById(Long.valueOf(user.getId())) == 0) {
                    authorRepo.save(new Author(Long.valueOf(user.getId()), user.getName(), user.getUsername(), false));
                }
            }
            int counterAdd = 0;
            for (Datum data : responseTweets.getData()) {
                if (tweetRepo.countTweetById(Long.valueOf(data.getId())) == 0) {
                    counterAdd++;
                    tweetRepo.save(new Tweet(Long.valueOf(data.getId()),
                            formatter.parse(data.getCreatedAt()),
                            data.getText(),
                            authorRepo.findById(Long.valueOf(data.getAuthorId())).get(),
                            data.getLang(),
                            keywordRepo.findById(keyword.getId()).get(),
                            false));
                }
            }
            keyword.setTwitCounterLastSearch(counterAdd);
            keyword.setTwitCounter(keyword.getTwitCounter() + counterAdd);
            keyword.setKeywordIsAuthor(authorRepo.existsAuthorByUserName(keyword.getName()));
            keywordRepo.save(keyword);
            for (Author author : authorRepo.findAuthorByFavouriteTrue()) {
                getTweetsByAuthorId(String.valueOf(author.getId()));
            }
        }
    }
}
