package com.redone.taskflow.services;

import com.redone.taskflow.dto.tagDto.TagRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TagService {

    ResponseEntity<Map<String, Object>> addTag(TagRequestDto tagRequestDto);
}
