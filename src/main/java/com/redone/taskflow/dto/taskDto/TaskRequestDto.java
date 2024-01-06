package com.redone.taskflow.dto.taskDto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Task name cannot be blank")
    @Size(min = 5, max = 25, message = "Task name size should be between 5 and 25 characters")
    private String name;
    @NotBlank(message = "Task description cannot be blank")
    @Size(min=5 , max = 250, message = "Task description size should be between 5 and 250 characters")
    private String description;
    @NotNull(message = "Start date cannot be null")
    @Future(message = "You Can't Create a Task in The Past")
    private LocalDate startDate;
    @NotNull(message = "End date cannot be null")
    @Future(message = "End date should be in the future")
    private LocalDate endDate;
    @Size(min = 2 , message = "you should enter at least tow tags")
    private List<Long> tags ;

}
