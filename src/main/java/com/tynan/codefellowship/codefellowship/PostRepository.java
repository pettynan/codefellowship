package com.tynan.codefellowship.codefellowship;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthorIn(Set<AppUser> appUserList);

}
