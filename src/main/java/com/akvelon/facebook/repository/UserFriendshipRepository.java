package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserFriendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendshipRepository extends JpaRepository<UserFriendship, Long> {

    @Query("select u from UserFriendship ur left join User u on ur.fromUserId=u.id where ur.toUserId=?1 and ur.action=?2")
    List<User> getUserAllByToUserIdAndAction(Long userId, String action);

    Boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    Optional<UserFriendship> getByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
