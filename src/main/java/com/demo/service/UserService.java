package com.demo.service;

import com.demo.dto.UserDto;
import com.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDto userDto);
    User updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    User getUserById(Long id);
    Page<User> getAllUsers(Pageable pageable);
} 