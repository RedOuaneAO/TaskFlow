package com.redone.taskflow.mapper;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TaskModificationMapper {

    TaskModificationMapper INSTANCE= Mappers.getMapper(TaskModificationMapper.class);
    @Mapping(target = "demandedBy" , source = "demandedBy" ,qualifiedByName = "mapDemandedBy")
    @Mapping(target = "currentTask" , source = "currentTask" ,qualifiedByName = "mapCurrentTask")
    @Mapping(target = "replacementTask" , source = "replacementTask" ,qualifiedByName = "mapReplacementTask")
    TaskModification entityToTaskModDto(ModificationRequestDto modificationRequestDto);



    @Named("mapDemandedBy")
    default User mapDemandedBy(Long demandedBy){
        return User.builder().id(demandedBy).build();
    }
    @Named("mapCurrentTask")
    default Task mapCurrentTask(Long currentTask){
        return Task.builder().id(currentTask).build();
    }
    @Named("mapReplacementTask")
    default Task mapReplacementTask(Long replacementTask){
        return Task.builder().id(replacementTask).build();
    }
}
