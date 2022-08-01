package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.Post;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long > {

    List<Post> findAllByOwner(User owner);

    @Query(value = "Select * from post p where p.user_group_id is null and p.user_id in ( " +
                    "        SELECT distinct fr.from_user_id  from user_friendship fr inner join " +
                    "        account a on a.id = fr.from_user_id where a.email=?1 " +
                    "      UNION " +
                    "        SELECT  distinct fr.to_user_id from user_friendship fr inner join " +
                    "        account a on a.id = fr.to_user_id where a.email=?1)", nativeQuery = true)
    List<Post> findFriendsPostsByEmail(String email);

    List<Post> findAllByUserGroupOrderByPostedDateDesc(UserGroup userGroup);
}
