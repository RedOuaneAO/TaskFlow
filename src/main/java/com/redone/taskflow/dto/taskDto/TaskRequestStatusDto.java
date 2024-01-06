package com.redone.taskflow.dto.taskDto;

import com.redone.taskflow.demain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestStatusDto {
    @NotBlank(message = "Please Enter The task you want to change its status")
    private Long id;
    private TaskStatus taskStatus;
}
