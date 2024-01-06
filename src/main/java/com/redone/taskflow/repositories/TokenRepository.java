package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.Token;
import com.redone.taskflow.demain.models.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    List<Token> findByUser(User user);
}
