package com.redone.taskflow.mapper;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import com.redone.taskflow.dto.userDto.UserRequestDto;
import com.redone.taskflow.dto.userDto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToEntity(UserRequestDto userRequestDto);
    UserResponseDto entityToUserDto(User user);

}
