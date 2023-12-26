package com.redone.taskflow.dto.taskDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
