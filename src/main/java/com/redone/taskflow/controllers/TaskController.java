package com.redone.taskflow.controllers;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponsetDto;
import com.redone.taskflow.services.TaskService;
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
    public ResponseEntity<Map<String ,Object>> addTask(@RequestBody TaskRequestDto taskRequestDto){
        return taskService.addTask(taskRequestDto);
    }
    @PutMapping("update_task")
    public TaskResponsetDto updateTask(@RequestBody TaskRequestDto taskRequestDto){
        return null;
    }
    @DeleteMapping("delete_task")
    public TaskResponsetDto deleteTask(@RequestBody TaskRequestDto taskRequestDto){
        return null;
    }
    @GetMapping("tasks")
    public List<Task> getAll(){
        return taskService.getAllTasks();
    }

}
