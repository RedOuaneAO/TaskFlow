package com.redone.taskflow.mapper;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TaskModificationMapper {

    TaskModificationMapper INSTANCE= Mappers.getMapper(TaskModificationMapper.class);
    @Mapping(target = "demandedBy" , source = "demandedBy" ,qualifiedByName = "mapDemandedByDto")
    @Mapping(target = "currentTask" , source = "currentTask" ,qualifiedByName = "mapCurrentTaskDto")
    @Mapping(target = "replacementTask" , source = "replacementTask" ,qualifiedByName = "mapReplacementTaskDto")
    TaskModification modDtoToTaskEntity(ModificationRequestDto modificationRequestDto);

    @Mapping(target = "demandedBy" , source = "demandedBy" ,qualifiedByName = "mapDemandedByEntity")
    @Mapping(target = "currentTask" , source = "currentTask" ,qualifiedByName = "mapCurrentTaskEntity")
    @Mapping(target = "replacementTask" , source = "replacementTask" ,qualifiedByName = "mapReplacementTaskEntity")
    ModificationResponseDto entityToTaskModDto(TaskModification taskModification);



    @Named("mapDemandedByDto")
    default User mapDemandedBy(Long demandedBy){return User.builder().id(demandedBy).build();}
    @Named("mapCurrentTaskDto")
    default Task mapCurrentTask(Long currentTask){return Task.builder().id(currentTask).build();}
    @Named("mapReplacementTaskDto")
    default Task mapReplacementTask(Long replacementTask){
        return Task.builder().id(replacementTask).build();
    }

    @Named("mapDemandedByEntity")
    default String mapDemandedByEntity(User user){
        return user.getUserName();
    }
    @Named("mapCurrentTaskEntity")
    default Long mapCurrentTaskEntity(Task currentTask){
        return currentTask.getId();
    }
    @Named("mapReplacementTaskEntity")
    default Long mapReplacementTaskEntity(Task replacementTask){
        if(replacementTask==null){
            return 0l;
        }
        return replacementTask.getId();
    }
}
