package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.ModificationStatue;
import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.enums.TokenType;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskDto.TaskResponseDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationResponseDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationStatusDto;
import com.redone.taskflow.handler.customExceptions.DemandNotFoundException;
import com.redone.taskflow.handler.customExceptions.TaskNotFoundException;
import com.redone.taskflow.handler.customExceptions.UserNotFoundException;
import com.redone.taskflow.mapper.TaskModificationMapper;
import com.redone.taskflow.repositories.TaskModificationRepository;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.repositories.TokenRepository;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TaskModificationService;
import com.redone.taskflow.services.TaskService;
import com.redone.taskflow.services.TokenService;
import com.redone.taskflow.services.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskModificationServiceImpl implements TaskModificationService {
    private  TaskModificationRepository modificationRepository;
    private  TaskService taskService;
    private  UserService userService;
    private  TokenService tokenService;
    private  TaskModificationMapper modificationMapper;
    private Map<String ,Object> response = new HashMap<String , Object>();
    @Autowired
    public TaskModificationServiceImpl(TaskModificationRepository modificationRepository, TaskService taskService, UserService userService, TokenService tokenService, TaskModificationMapper modificationMapper) {
        this.modificationRepository = modificationRepository;
        this.taskService = taskService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.modificationMapper = modificationMapper;
    }
    public TaskModificationServiceImpl(TokenService tokenService) {this.tokenService = tokenService;}
    public TaskModificationServiceImpl(){}
    @Override
    public ResponseEntity<Map<String, Object>> taskReplacement(ModificationRequestDto modificationRequestDto) {
        User demandedBy = userService.findById(modificationRequestDto.getDemandedBy()).orElseThrow(()->new UserNotFoundException("this user doesn't exist"));
        Task currentTask = taskService.findById(modificationRequestDto.getCurrentTask()).orElseThrow(()->new TaskNotFoundException("the current task doesn't exist"));
        if(!currentTask.getAssignedTo().equals(demandedBy)){throw new RuntimeException("the task you want to replace is assign to another user");}
        tokenService.refreshToken(demandedBy);
        boolean tokenAvailable =tokensAvailablity(demandedBy , modificationRequestDto.getType());
        if (tokenAvailable){
            if(modificationRequestDto.getType().equals(ModificationType.REPLACE)){
                 replaceTask(modificationRequestDto,demandedBy,currentTask);
            }else {
                if(currentTask.getUser().equals(demandedBy)){
                    taskService.delete(currentTask);
                    response.put("state" , "success");
                    response.put("message", "The task has been Deleted seccessfully");
                    return ResponseEntity.ok(response);
                }
                deleteTask(currentTask,demandedBy,modificationRequestDto);
            }
            updateTokens(demandedBy ,modificationRequestDto.getType());
            response.put("state" , "success");
            response.put("message", "your demand has been sent seccessfully");
        }else {
            response.put("state" , "error");
            response.put("message", "your don't have enought tokens");
        }
        return ResponseEntity.ok(response);
    }

    private void deleteTask(Task currentTask, User demandedBy, ModificationRequestDto modificationRequestDto) {
        TaskModification taskDelete = TaskModification.builder().demandedBy(demandedBy).currentTask(currentTask).statue(ModificationStatue.PENDING).type(modificationRequestDto.getType()).demandDate(LocalDateTime.now()).build();
        modificationRepository.save(taskDelete);
    }

    private void replaceTask(ModificationRequestDto modificationRequestDto, User demandedBy, Task currentTask) {
        Task replacementTask = taskService.findById(modificationRequestDto.getReplacementTask()).orElseThrow(()->new TaskNotFoundException("the replecement task doesn't exist"));
        TaskModification taskModification = TaskModification.builder().demandedBy(demandedBy).currentTask(currentTask).statue(ModificationStatue.PENDING).replacementTask(replacementTask).type(modificationRequestDto.getType()).demandDate(LocalDateTime.now()).build();
        modificationRepository.save(taskModification);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getReplacementDemand() {
        List<TaskModification> taskModification =  modificationRepository.findAll();
        List<ModificationResponseDto> modificationResponseDtoList= taskModification.stream().map(taskModification1 -> modificationMapper.entityToTaskModDto(taskModification1)).collect(Collectors.toList());
        response.put("status" ,"success");
        response.put("demands" , modificationResponseDtoList);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> changeDemandStatus(ModificationStatusDto modificationStatusDto) {
        TaskModification taskModification = modificationRepository.findById(modificationStatusDto.getDemandId()).orElseThrow(()->new DemandNotFoundException("this demand doesn't exist"));
        taskModification.setStatue(modificationStatusDto.getStatus());
        Task currentTask = taskService.findById(taskModification.getCurrentTask().getId()).orElseThrow(()->new TaskNotFoundException("the current Task is no longer exist"));
        if(modificationStatusDto.getStatus().equals(ModificationStatue.ACCEPTED)){
            if( taskModification.getType().equals(ModificationType.REPLACE)){
                Task replacementTask  = taskService.findById(taskModification.getReplacementTask().getId()).orElseThrow(()->new TaskNotFoundException("this Task is no longer exist"));
                currentTask.setAssignedTo(replacementTask.getAssignedTo());
                replacementTask.setAssignedTo(taskModification.getDemandedBy());
                taskService.save(replacementTask);
                taskService.save(currentTask);
            } else if (taskModification.getType().equals(ModificationType.DELETE)){
                taskService.delete(currentTask);
            }
        }
        ModificationResponseDto responseDto = modificationMapper.entityToTaskModDto(modificationRepository.save(taskModification));
        response.put("status" ,"success");
        response.put("message" ,"the status has been changed successfuly");
        response.put("demand",responseDto);
        return ResponseEntity.ok(response);
    }

    public boolean tokensAvailablity(User user, ModificationType type) {
        List<Token> tokens = tokenService.findByUser(user);
        for (Token token:tokens){
            if (type.equals(ModificationType.REPLACE) && token.getTokenType().equals(TokenType.UPDATE) && token.getNumber()>0){
                return true;
            }else if (type.equals(ModificationType.DELETE) && token.getTokenType().equals(TokenType.DELETE) && token.getNumber()>0){
                return true;
            }
        }
        return false;
    }
    public void updateTokens(User user,ModificationType type) {
        List<Token> tokens = tokenService.findByUser(user);
        for (Token token:tokens){
            if (type.equals(ModificationType.REPLACE) && token.getTokenType().equals(TokenType.UPDATE)){
                token.setNumber(token.getNumber()-1);
            }else if(type.equals(ModificationType.DELETE) && token.getTokenType().equals(TokenType.DELETE)){
                token.setNumber(token.getNumber()-1);
            }
            tokenService.save(token);
        }
    }
    @Scheduled(fixedRate = 3600000)
    public void extraTokens(){
            List<TaskModification> taskModifications=modificationRepository.findAll();
            ZoneId zoneId =ZoneId.of("Africa/Casablanca");
            taskModifications.stream().filter(taskModification -> ChronoUnit.HOURS.between(taskModification.getDemandDate(),LocalDateTime.now(zoneId))>=12 && taskModification.getStatue().equals(ModificationStatue.PENDING)).forEach(taskModification -> {
                List<Token> tokens = tokenService.findByUser(taskModification.getDemandedBy());
                for (Token token:tokens) {
                    if(token.getTokenType().equals(TokenType.UPDATE)){
                        token.setNumber(4);
                        if(LocalDateTime.now(zoneId).toLocalDate().isAfter(taskModification.getDemandDate().toLocalDate())) {
                            token.setAddDate(LocalDateTime.now(zoneId).toLocalDate());
                        }else if(LocalDateTime.now(zoneId).toLocalDate().isEqual(taskModification.getDemandDate().toLocalDate())){
                            token.setAddDate(LocalDateTime.now(zoneId).plusHours(12).toLocalDate());
                        }
                    }
                        tokenService.save(token);
                }
            });
    }

}
