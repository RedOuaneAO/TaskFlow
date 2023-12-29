package com.redone.taskflow.dto.taskDto;

import com.redone.taskflow.demain.models.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssigneDto {
    private Long taskId;
    private Long assignedTo;
}
