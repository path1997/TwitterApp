package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    int countAuthorById(Long id);
    boolean existsAuthorByUserName(String username);
}
