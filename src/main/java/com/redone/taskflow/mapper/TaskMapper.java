package com.redone.taskflow.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.redone.taskflow.demain.models.Tag;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskDto.TaskAssigneDto;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponseDto entityToTaskDto(Task task);
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    Task taskDtoToEntity(TaskRequestDto taskRequestDto);

    @Named("mapTags")
    default List<Tag> mapTags(List<Long> tagIds) {
        List<Tag> tags = new ArrayList<>();
        for (Long tagId : tagIds) {
            Tag tag = new Tag();
            tag.setId(tagId);
            tags.add(tag);
        }
        return tags;
    }
}
