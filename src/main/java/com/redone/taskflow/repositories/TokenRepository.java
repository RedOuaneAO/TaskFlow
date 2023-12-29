package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token,Long> {

    List<Token> findByUser(User user);
}
