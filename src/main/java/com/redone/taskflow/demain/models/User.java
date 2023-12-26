package com.redone.taskflow.demain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "user")
    private List<Task> task;
    @OneToMany(mappedBy="user")
    private List<Token> tokens;

}
