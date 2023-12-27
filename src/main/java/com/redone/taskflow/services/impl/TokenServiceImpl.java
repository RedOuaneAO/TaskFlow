package com.redone.taskflow.services.impl;

import com.redone.taskflow.demain.enums.TokenType;
import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import com.redone.taskflow.repositories.TokenRepository;
import com.redone.taskflow.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;



    public List<Token> generateToken(User user){
        List<Token> tokenList = new ArrayList<>();
        Token deleteToken = Token.builder().user(user).tokenType(TokenType.DELETE).number(1).addDate(LocalDate.now()).build();
        Token updateToken = Token.builder().user(user).tokenType(TokenType.UPDATE).number(2).addDate(LocalDate.now()).build();
        tokenList.add(deleteToken);
        tokenList.add(updateToken);
        for (Token token:tokenList) {
            tokenRepository.save(token);
        }
        return tokenList;
    }
//    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshToken(){
        System.out.println("refresh");
        List<Token> tokens= tokenRepository.findAll();
        if(!tokens.isEmpty()){
            for (Token token:tokens) {
                if (token.getTokenType().equals(TokenType.UPDATE)){  //token.getAddDate().isBefore(LocalDate.now()) &&
                    token.setNumber(2);
                }
                tokenRepository.save(token);
            }
        }
    }
}
