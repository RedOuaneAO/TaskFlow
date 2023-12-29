package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.enums.TokenType;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.dto.taskModificationDto.ModificationRequestDto;
import com.redone.taskflow.repositories.TaskModificationRepository;
import com.redone.taskflow.repositories.TaskRepository;
import com.redone.taskflow.repositories.TokenRepository;
import com.redone.taskflow.repositories.UserRepository;
import com.redone.taskflow.services.TaskModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class TaskModificationServiceImpl implements TaskModificationService {

    private final TaskModificationRepository modificationRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    @Override
    public ResponseEntity<Map<String, Object>> taskReplacement(ModificationRequestDto modificationRequestDto) {
        Map<String ,Object> response = new HashMap<String , Object>();
        User user = userRepository.findById(modificationRequestDto.getDemandedBy()).orElseThrow(()->new RuntimeException("this user doesn't exist"));
        Task currentTask = taskRepository.findById(modificationRequestDto.getCurrentTask()).orElseThrow(()->new RuntimeException("this task doesn't exist"));
        if(modificationRequestDto.getType().equals(ModificationType.REPLACE)){
            Task replacementTask = taskRepository.findById(modificationRequestDto.getReplacementTask()).orElseThrow(()->new RuntimeException("this task doesn't exist"));
            TaskModification taskModification = TaskModification.builder().demandedBy(user).currentTask(currentTask).replacementTask(replacementTask).type(modificationRequestDto.getType()).demandDate(LocalDate.now()).build();
            Boolean tokenAvailable =tokensAvailablity(user , modificationRequestDto.getType());
            if (tokenAvailable){
                modificationRepository.save(taskModification);
                updateTokens(user);
                response.put("state" , "success");
                response.put("message", "your demand has been sent seccessfully");
            }else {
                response.put("state" , "error");
                response.put("message", "your don't have enought tokens");
            }
        }
        return ResponseEntity.ok(response);
    }

    private Boolean tokensAvailablity(User user, ModificationType type) {
        List<Token> tokens = tokenRepository.findByUser(user);
        for (Token token:tokens){
            if (type.equals(ModificationType.REPLACE) && token.getTokenType().equals(TokenType.UPDATE) && token.getNumber()>0){
                return true;
            }else if (type.equals(ModificationType.DELETE) && token.getTokenType().equals(TokenType.DELETE) && token.getNumber()>0){
                return true;
            }
        }
        return false;
    }

    private void updateTokens(User user) {
        List<Token> tokens = tokenRepository.findByUser(user);
        for (Token token:tokens){
            if (token.getTokenType().equals(TokenType.UPDATE) && token.getNumber()>0){
                token.setNumber(token.getNumber()-1);
                tokenRepository.save(token);
            }
        }
    }
}
