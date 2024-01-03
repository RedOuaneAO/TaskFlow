package com.redone.taskflow.demain.models;

import com.redone.taskflow.demain.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private String userName;
    private String password;
    private String email;
    private boolean isAdmin;
    @OneToMany(mappedBy = "user" , fetch = FetchType.EAGER)
    private List<Task> task;
    @OneToMany(mappedBy="user" , fetch = FetchType.EAGER)
    private List<Token> tokens;




}
