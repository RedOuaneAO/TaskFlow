package com.redone.taskflow.demain.models;

import com.redone.taskflow.demain.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private TokenType tokenType;
    private int number;
    private LocalDate addDate;
    @ManyToOne
    private User user;
}
