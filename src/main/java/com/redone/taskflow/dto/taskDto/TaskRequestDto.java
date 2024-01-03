package com.redone.taskflow.dto.taskDto;

import com.redone.taskflow.demain.enums.TaskStatus;
import com.redone.taskflow.demain.models.Tag;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
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
    @Future(message = "You Can't Create a Task in The Past")
    private LocalDate startDate;
    private LocalDate endDate;
    @Size(min = 2 , message = "you should enter at least tow tags")
    private List<Long> tags ;


}
