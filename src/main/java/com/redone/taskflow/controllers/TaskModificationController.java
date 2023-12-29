package com.redone.taskflow.controllers;

import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.services.TaskModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TaskModificationController {
    private final TaskModificationService taskModificationService;

    @PostMapping("task_change")
    public ResponseEntity<Map<String ,Object>> taskReplacement(@RequestBody ModificationRequestDto modificationRequestDto){
        return taskModificationService.taskReplacement(modificationRequestDto);
    }
}
