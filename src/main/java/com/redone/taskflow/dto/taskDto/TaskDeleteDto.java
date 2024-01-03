package com.redone.taskflow.dto.taskDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDeleteDto {
    private Long deletedById;
    private Long taskId;
}
