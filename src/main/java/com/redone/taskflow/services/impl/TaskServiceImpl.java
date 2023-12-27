package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskRequestStatusDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import com.redone.taskflow.mapper.TaskMapper;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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
        if(taskRequestDto.getEndDate().minus(Period.ofDays(3)).isBefore(taskRequestDto.getStartDate())){
            throw new RuntimeException("the end day must be in 3 day of start date");
        }
        taskRepository.save(task);
        TaskResponseDto taskResponsetDto= taskMapper.entityToTaskDto(task);
        response.put("status", "success");
        response.put("message", "your Task is created successfuly ");
        response.put("Task",taskResponsetDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public ResponseEntity<Map<String,Object>> changeStatus(TaskRequestStatusDto taskRequestStatusDto) {
        Map<String ,Object> response = new HashMap<String , Object>();
        Task  task = taskRepository.findById(taskRequestStatusDto.getId()).orElseThrow(()->new RuntimeException("this task dosen't exist"));
        if (task.getEndDate().isBefore(LocalDate.now())){
            throw new RuntimeException("the time is already over");
        }
        task.setStatus(taskRequestStatusDto.getTaskStatus());
        Task task1 =taskRepository.save(task);
        response.put("status" , "Success");
        response.put("message" , "the status has been changed successufly");
        return ResponseEntity.ok(response);
    }
}
