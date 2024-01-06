package com.redone.taskflow.services.impl;

import com.redone.taskflow.dto.tagDto.TagRequestDto;
import com.redone.taskflow.mapper.TagMapper;
import com.redone.taskflow.repositories.TagRepository;
import com.redone.taskflow.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    @Override
    public ResponseEntity<Map<String, Object>> addTag(TagRequestDto tagRequestDto) {
        Map<String,Object> response =new HashMap<String,Object>();
        response.put("state" , "success");
        response.put("message" , "The Tag has been created successfully");
        tagRepository.save(tagMapper.dtoToTagEntity(tagRequestDto));
        return ResponseEntity.ok(response);
    }
}
