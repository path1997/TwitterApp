package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Twit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitRepo extends CrudRepository<Twit,Long> {
}
