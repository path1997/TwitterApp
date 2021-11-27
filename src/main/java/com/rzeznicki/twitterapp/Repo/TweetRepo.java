package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Keyword;
import com.rzeznicki.twitterapp.Entities.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepo extends CrudRepository<Tweet,Long> {
    public int countTweetById(Long id);
    public List<Tweet> findAllByKeywordIdOrderByCreatedAtDesc(Long id);
    public void deleteAllByKeywordId(Long id);
}
