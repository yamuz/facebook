package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long userId);
    List<UserDto> findAll();

    User findByEmail(String email);

    UserDto save(UserDto userDto);

    boolean existsByEmail(String email);

    UserDto update(UserDto userDto);
}
