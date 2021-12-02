package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepo extends CrudRepository<Tweet,Long> {
    int countTweetById(Long id);
    List<Tweet> findAllByKeywordIdAndDeletedFalseOrderByCreatedAtDesc(Long id);
    void deleteAllByKeywordId(Long id);
    List<Tweet> findAllByAuthorUserNameAndDeletedFalseOrderByCreatedAtDesc(String name);
    List<Tweet> findAllByAuthorIdAndDeletedIsFalseOrderByCreatedAtDesc(Long id);
    List<Tweet> findAllByDeletedIsTrueOrderByCreatedAtDesc();
    void deleteAllByAuthor_IdAndAuthorFavourite(Long id, boolean favourite);
}
