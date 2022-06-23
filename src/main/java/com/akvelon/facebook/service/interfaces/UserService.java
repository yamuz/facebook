package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.entity.User;

public interface UserService {
    User findById(Long userId);
}
