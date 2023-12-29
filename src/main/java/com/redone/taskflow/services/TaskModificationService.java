package com.redone.taskflow.services;

import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface TaskModificationService {
    ResponseEntity<Map<String, Object>> taskReplacement(ModificationRequestDto modificationRequestDto);
}
