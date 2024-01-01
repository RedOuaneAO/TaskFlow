package com.redone.taskflow.services;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskAssigneDto;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskRequestStatusDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TaskService {
    ResponseEntity<Map<String ,Object>> addTask(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> getAllTasks();

    ResponseEntity<Map<String,Object>> changeStatus(TaskRequestStatusDto taskRequestStatusDto);

    ResponseEntity<Map<String, Object>> assignTask(TaskAssigneDto taskAssigneDto);
    Optional<Task> findById(Long id);

}
