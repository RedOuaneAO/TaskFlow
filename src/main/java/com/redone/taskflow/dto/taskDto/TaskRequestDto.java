package com.redone.taskflow.dto.taskDto;

import com.redone.taskflow.demain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String name;
    private String description;
//    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Long assignedTo;


}
