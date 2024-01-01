package com.redone.taskflow.dto.taskModificationDto;

import com.redone.taskflow.demain.enums.ModificationStatue;
import com.redone.taskflow.demain.enums.ModificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificationResponseDto {
    private String demandedBy;
    private ModificationType type;
    private ModificationStatue statue;
    private Long currentTask;
    private Long replacementTask;
}
