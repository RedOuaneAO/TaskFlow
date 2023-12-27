package com.redone.taskflow.services;

import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.userDto.UserRequestDto;
import com.redone.taskflow.dto.userDto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
