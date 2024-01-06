package com.redone.taskflow.services;

import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface TaskModificationService {
    ResponseEntity<Map<String, Object>> taskReplacement(ModificationRequestDto modificationRequestDto);
    ResponseEntity<Map<String, Object>> getReplacementDemand();
    ResponseEntity<Map<String, Object>> changeDemandStatus(ModificationStatusDto modificationStatusDto);
}
