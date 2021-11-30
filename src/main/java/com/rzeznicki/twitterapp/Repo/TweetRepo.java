package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Keyword;
import com.rzeznicki.twitterapp.Entities.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepo extends CrudRepository<Tweet,Long> {
    int countTweetById(Long id);
    List<Tweet> findAllByKeywordIdAndDeletedFalseOrderByCreatedAt(Long id);
    void deleteAllByKeywordId(Long id);
    List<Tweet> findAllByAuthorUserName(String name);
    List<Tweet> findAllByAuthorIdAndDeletedIsFalse(Long id);
    List<Tweet> findAllByDeletedIsTrue();
}
