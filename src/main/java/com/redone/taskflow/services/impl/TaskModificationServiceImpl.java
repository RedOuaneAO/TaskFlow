package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.ModificationStatue;
import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.enums.TokenType;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationResponseDto;
import com.redone.taskflow.dto.taskModificationDto.ModificationStatusDto;
import com.redone.taskflow.mapper.TaskModificationMapper;
import com.redone.taskflow.repositories.TaskModificationRepository;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.repositories.TokenRepository;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TaskModificationService;
import com.redone.taskflow.services.TaskService;
import com.redone.taskflow.services.TokenService;
import com.redone.taskflow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskModificationServiceImpl implements TaskModificationService {

    private final TaskModificationRepository modificationRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final TokenService tokenService;
    private final TaskModificationMapper modificationMapper;
    private Map<String ,Object> response = new HashMap<String , Object>();
    @Override
    public ResponseEntity<Map<String, Object>> taskReplacement(ModificationRequestDto modificationRequestDto) {
        User user = userService.findById(modificationRequestDto.getDemandedBy()).orElseThrow(()->new RuntimeException("this user doesn't exist"));
        Task currentTask = taskService.findById(modificationRequestDto.getCurrentTask()).orElseThrow(()->new RuntimeException("this task doesn't exist"));
        boolean tokenAvailable =tokensAvailablity(user , modificationRequestDto.getType());
        if (tokenAvailable){
            if(modificationRequestDto.getType().equals(ModificationType.REPLACE)){
                Task replacementTask = taskService.findById(modificationRequestDto.getReplacementTask()).orElseThrow(()->new RuntimeException("this task doesn't exist"));
                TaskModification taskModification = TaskModification.builder().demandedBy(user).currentTask(currentTask).statue(ModificationStatue.PENDING).replacementTask(replacementTask).type(modificationRequestDto.getType()).demandDate(LocalDate.now()).build();
                modificationRepository.save(taskModification);
            }else {
                TaskModification taskDelete = TaskModification.builder().demandedBy(user).currentTask(currentTask).statue(ModificationStatue.PENDING).type(modificationRequestDto.getType()).demandDate(LocalDate.now()).build();
                modificationRepository.save(taskDelete);
            }
            updateTokens(user ,modificationRequestDto.getType());
            response.put("state" , "success");
            response.put("message", "your demand has been sent seccessfully");
        }else {
            response.put("state" , "error");
            response.put("message", "your don't have enought tokens");
        }
        return ResponseEntity.ok(response);
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
    public ResponseEntity<Map<String, Object>> changeDemandStats(ModificationStatusDto modificationStatusDto) {
        TaskModification taskModification = modificationRepository.findById(modificationStatusDto.getDemandId()).orElseThrow(()->new RuntimeException("this demand doesn't exist"));
        taskModification.setStatue(modificationStatusDto.getStatus());
        ModificationResponseDto responseDto = modificationMapper.entityToTaskModDto(modificationRepository.save(taskModification));
        response.put("status" ,"success");
        response.put("message" ,"the status has been changed successfuly");
        response.put("demand",responseDto);
        return ResponseEntity.ok(response);
    }

    private boolean tokensAvailablity(User user, ModificationType type) {
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

    private void updateTokens(User user,ModificationType type) {
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
}
