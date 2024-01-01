package com.redone.taskflow.services;

import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.userDto.UserRequestDto;
import com.redone.taskflow.dto.userDto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    Optional<User> findById(Long id);
}
