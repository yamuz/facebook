package com.akvelon.facebook.repository;


import com.akvelon.facebook.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getBySenderUserIdAndReceivedUserId(Long userId, Long friendId);
}
