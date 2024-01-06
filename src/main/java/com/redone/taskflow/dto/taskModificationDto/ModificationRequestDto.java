package com.redone.taskflow.dto.taskModificationDto;

import com.redone.taskflow.demain.enums.ModificationStatue;
import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificationRequestDto {
    @NotNull(message = "Demanded by shouldn't be null")
    @Positive(message = "Demanded by should be a positive number")
    private Long demandedBy;
    @NotNull(message = "Modification type shouldn't be null")
    private ModificationType type;
    @NotNull(message = "Current task shouldn't be null")
    @Positive(message = "Current task should be a positive number")
    private Long currentTask;
    @Nullable
    private Long replacementTask;
}
