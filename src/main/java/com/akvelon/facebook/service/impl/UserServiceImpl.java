package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.repository.UserRepository;
import com.akvelon.facebook.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

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
        return UserDto.from(userRepository.save(User.from(userDto)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("account not found by email"));

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getAddress() != null && !userDto.getAddress().isEmpty()) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getPhone() != null && !userDto.getPhone().isEmpty()) {
            user.setPhone(userDto.getPhone());
        }
        return UserDto.from(userRepository.save(user));
    }
}
