package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.Post;
import com.akvelon.facebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long > {

    List<Post> findAllByOwner(User owner);

    @Query(value = "Select p from post p where p.user_id in (select distinct (CASE WHEN from_user_id =?1 " +
                                " THEN to_user_id " +
                              " ELSE from_user_id " +
                         " END) from friendship f where f.from_user_id =?1 or f.to_user_id=?1)",
            nativeQuery = true)
    List<Post> findFriendsPostsByEmail(String email);
}
