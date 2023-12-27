package com.redone.taskflow.services;

import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenService {
   List<Token> generateToken(User user);
}
