package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponsetDto;
import com.redone.taskflow.mapper.TaskMapper;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;
    @Override
    public ResponseEntity<Map<String ,Object>> addTask(TaskRequestDto taskRequestDto) {
        Map<String,Object> response = new HashMap<String ,Object>();
        Task task = taskMapper.taskDtoToEntity(taskRequestDto);
        taskRepository.save(task);
        TaskResponsetDto taskResponsetDto= taskMapper.entityToTaskDto(task);
        response.put("status", "success");
        response.put("message", "your Task is created successfuly ");
        response.put("Task",taskResponsetDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
