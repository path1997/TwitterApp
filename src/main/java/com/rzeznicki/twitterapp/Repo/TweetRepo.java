package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepo extends CrudRepository<Tweet,Long> {
    public int countTweetById(Long id);
}
