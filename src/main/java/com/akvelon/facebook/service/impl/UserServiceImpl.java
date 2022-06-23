package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.UserDto;
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

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto save(UserDto userDto) {
        return  UserDto.from(userRepository.save(User.from(userDto)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
