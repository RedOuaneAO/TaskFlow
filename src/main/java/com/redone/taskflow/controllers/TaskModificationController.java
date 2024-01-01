package com.redone.taskflow.controllers;

import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationStatusDto;
import com.redone.taskflow.services.TaskModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("replacement_demand")
    public ResponseEntity<Map<String ,Object>> getAllReplacementDemand(){
        return taskModificationService.getReplacementDemand();
    }

    @PutMapping("replacement_status")
    public ResponseEntity<Map<String ,Object>> changeDemandStats(@RequestBody ModificationStatusDto modificationStatusDto){
        return taskModificationService.changeDemandStatus(modificationStatusDto);
    }
}
