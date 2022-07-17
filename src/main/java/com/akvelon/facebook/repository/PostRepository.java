package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.Post;
import com.akvelon.facebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long > {

    List<Post> findAllByOwner(User owner);
}
