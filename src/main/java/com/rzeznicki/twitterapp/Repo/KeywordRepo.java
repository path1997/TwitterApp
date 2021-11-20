package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepo extends CrudRepository<Keyword, Long> {
}
