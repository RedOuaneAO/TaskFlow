package com.redone.taskflow.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponsetDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    TaskResponsetDto entityToTaskDto(Task task);
    Task taskDtoToEntity(TaskRequestDto taskRequestDto);
}
