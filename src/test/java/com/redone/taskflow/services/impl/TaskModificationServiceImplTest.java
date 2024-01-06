package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.enums.TokenType;
import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskModificationServiceImplTest {

    private TaskModificationServiceImpl taskModificationService;
    @Mock
    private TokenService tokenService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskModificationService =new TaskModificationServiceImpl(tokenService);
    }
    @Test
    void tokensAvailablity(){
        User user =User.builder().id(1l).build();
        Token token =Token.builder().id(1L).tokenType(TokenType.UPDATE).user(user).number(2).build();
        Token token1 = Token.builder().id(2L).tokenType(TokenType.DELETE).user(user).number(1).build();
        List<Token> tokens =new ArrayList<>();
        tokens.add(token);
        tokens.add(token1);
        Mockito.when(tokenService.findByUser(user)).thenReturn(tokens);
//        assertEquals(taskModificationService.tokensAvailablity(user,ModificationType.REPLACE),true);
        assertTrue(taskModificationService.tokensAvailablity(user,ModificationType.REPLACE));
    }




}