package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.TaskStatus;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskDto.TaskAssigneDto;
import com.redone.taskflow.dto.taskDto.TaskRequestDto;
import com.redone.taskflow.dto.taskDto.TaskRequestStatusDto;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import com.redone.taskflow.mapper.TaskMapper;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<Map<String ,Object>> addTask(TaskRequestDto taskRequestDto) {
        Map<String,Object> response = new HashMap<String ,Object>();
        Task task = taskMapper.taskDtoToEntity(taskRequestDto);
        if(taskRequestDto.getEndDate().minus(Period.ofDays(3)).isBefore(taskRequestDto.getStartDate())){
            throw new RuntimeException("the end day must be in 3 day of start date");
        }
        task.setUser(User.builder().id(2L).build());
        task.setStatus(TaskStatus.TODO);
        taskRepository.save(task);
        TaskResponseDto taskResponsetDto= taskMapper.entityToTaskDto(task);
        response.put("status", "success");
        response.put("message", "your Task is created successfuly ");
        response.put("Task",taskResponsetDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<Task> taskList =taskRepository.findAll();
        List<TaskResponseDto> taskResponseDtos=taskList.stream().map(task -> taskMapper.entityToTaskDto(task)).collect(Collectors.toList());
        return taskResponseDtos;
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

    @Override
    public ResponseEntity<Map<String, Object>> assignTask(TaskAssigneDto taskAssigneDto) {
        Map<String,Object> response = new HashMap<String ,Object>();
        User assignTo = userRepository.findById(taskAssigneDto.getAssignedTo()).orElseThrow(()->new RuntimeException("this user doesn't exist"));
        Task task= taskRepository.findById(taskAssigneDto.getTaskId()).orElseThrow(()->new RuntimeException("this task doesn't exist" ));
        if(!task.getUser().equals(assignTo)){
            response.put("status","You Dont Have permission to  assign a task to another user");
            return ResponseEntity.badRequest().body(response);
        }
        task.setAssignedTo(assignTo);
        taskRepository.save(task);
        response.put("state" , "success");
        response.put("message", "the task has been assigned to "+ assignTo.getUserName());
        return ResponseEntity.ok(response);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

}
