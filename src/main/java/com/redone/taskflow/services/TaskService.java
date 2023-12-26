package com.redone.taskflow.services;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskResponsetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService {
    ResponseEntity<Map<String ,Object>> addTask(TaskRequestDto taskRequestDto);

    List<Task> getAllTasks();
}
