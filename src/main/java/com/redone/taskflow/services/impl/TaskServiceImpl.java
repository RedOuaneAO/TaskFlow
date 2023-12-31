package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.TaskStatus;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskDto.*;
import com.redone.taskflow.handler.customExceptions.TaskNotFoundException;
import com.redone.taskflow.handler.customExceptions.UserNotFoundException;
import com.redone.taskflow.mapper.TaskMapper;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TaskService;
import com.redone.taskflow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final UserService userService;
    private Map<String,Object> response = new HashMap<String ,Object>();

    @Override
    public ResponseEntity<Map<String ,Object>> addTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.taskDtoToEntity(taskRequestDto);
        if(taskRequestDto.getStartDate().minus(Period.ofDays(3)).isBefore(LocalDate.now())){
            throw new RuntimeException("The task must be scheduled 3 days from today");
        }
        if(taskRequestDto.getStartDate().isAfter(taskRequestDto.getEndDate())){
            throw new RuntimeException(("The endDate shouldn't be before startDate"));
        }
        task.setUser(User.builder().id(1L).build());
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
        User assignTo = userService.findById(taskAssigneDto.getAssignedTo()).orElseThrow(()->new UserNotFoundException("this user doesn't exist"));
        Task task= taskRepository.findById(taskAssigneDto.getTaskId()).orElseThrow(()->new TaskNotFoundException("this task doesn't exist" ));
        if(!task.getUser().equals(assignTo) && !task.getUser().isAdmin()){
            response.put("status","You Dont Have permission to  assign a task to another user");
            return ResponseEntity.badRequest().body(response);
        }
        task.setAssignedTo(assignTo);
        taskRepository.save(task);
        response.put("state" , "success");
        response.put("message", "the task has been assigned to "+ assignTo.getUserName());
        return ResponseEntity.ok(response);
    }

//    @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void checkTasks(){
        List<Task> taskList = taskRepository.findAll();
        for (Task task:taskList) {
            if(task.getEndDate().isBefore(LocalDate.now()) && !task.getStatus().equals(TaskStatus.DONE)){
                task.setStatus(TaskStatus.NOT_COMPLETED);
                taskRepository.save(task);
            }
        }

    }
    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

}
