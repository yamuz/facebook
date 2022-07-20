package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.repository.UserRepository;
import com.akvelon.facebook.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(RuntimeException::new);
    }
}
