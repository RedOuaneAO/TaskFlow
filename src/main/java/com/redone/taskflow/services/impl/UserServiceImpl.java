package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.userDto.UserRequestDto;
import com.redone.taskflow.dto.userDto.UserResponseDto;
import com.redone.taskflow.mapper.UserMapper;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TokenService;
import com.redone.taskflow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService ;
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userDtoToEntity(userRequestDto);
        UserResponseDto userResponseDto =userMapper.entityToUserDto(userRepository.save(user));
        tokenService.generateToken(user);
        return userResponseDto;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
