package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
