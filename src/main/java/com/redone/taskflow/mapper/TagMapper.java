package com.redone.taskflow.mapper;

import com.redone.taskflow.demain.models.Tag;
import com.redone.taskflow.dto.tagDto.TagRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag dtoToTagEntity(TagRequestDto tagRequestDto);
}
