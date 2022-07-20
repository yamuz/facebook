package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByName(String userGroupName);

    boolean existsByName(String userGroupName);
}
