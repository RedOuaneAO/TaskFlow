package com.redone.taskflow.demain.models;

import com.redone.taskflow.demain.enums.ModificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskModification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    private User demandedBy;
    private ModificationType type;
    private LocalDate demandDate;
    @OneToOne
    private Task currentTask;
    @OneToOne
    private Task replacementTask;
}