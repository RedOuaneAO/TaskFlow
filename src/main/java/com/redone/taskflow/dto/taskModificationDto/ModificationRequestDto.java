package com.redone.taskflow.dto.taskModificationDto;

import com.redone.taskflow.demain.enums.ModificationStatue;
import com.redone.taskflow.demain.enums.ModificationType;
import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificationRequestDto {
    private Long demandedBy;
    private ModificationType type;
    private ModificationStatue statue;
    private Long currentTask;
    private Long replacementTask;
}
