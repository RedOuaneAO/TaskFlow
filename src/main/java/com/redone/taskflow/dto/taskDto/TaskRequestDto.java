package com.redone.taskflow.dto.taskDto;

import com.redone.taskflow.demain.enums.TaskStatus;
import com.redone.taskflow.demain.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {
    private String name;
    private String description;
//    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> tags ;
//    private Long assignedTo;


}
