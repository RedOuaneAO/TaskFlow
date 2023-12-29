package com.redone.taskflow.controllers;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskAssigneDto;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskRequestStatusDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import com.redone.taskflow.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping("add_task")
    public ResponseEntity<Map<String ,Object>> addTask(@Valid  @RequestBody TaskRequestDto taskRequestDto){
        return taskService.addTask(taskRequestDto);
    }
    @PutMapping("update_task")
    public TaskResponseDto updateTask(@RequestBody TaskRequestDto taskRequestDto){
        return null;
    }
    @DeleteMapping("delete_task")
    public TaskResponseDto deleteTask(@RequestBody TaskRequestDto taskRequestDto){
        return null;
    }
    @PutMapping("task_status")
    public ResponseEntity<Map<String,Object>> changeStatus(@RequestBody TaskRequestStatusDto taskRequestStatusDto){
        return taskService.changeStatus(taskRequestStatusDto);
    }
    @GetMapping("tasks")
    public List<TaskResponseDto> getAll(){
        return taskService.getAllTasks();
    }
    @PutMapping("assign")
    public ResponseEntity<Map<String , Object>> assignTask(@RequestBody TaskAssigneDto taskAssigneDto){
        return taskService.assignTask(taskAssigneDto);
    }


}
