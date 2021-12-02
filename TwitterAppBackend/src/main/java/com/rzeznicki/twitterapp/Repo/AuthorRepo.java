package com.rzeznicki.twitterapp.Repo;

import com.rzeznicki.twitterapp.Entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    int countAuthorById(Long id);
    boolean existsAuthorByUserName(String username);
    List<Author> findAuthorByFavouriteTrue();
}
