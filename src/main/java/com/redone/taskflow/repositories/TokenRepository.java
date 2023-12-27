package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
