package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
