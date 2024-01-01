package com.redone.taskflow.dto.taskModificationDto;

import com.redone.taskflow.demain.enums.ModificationStatue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificationStatusDto {
    private Long demandId ;
    private ModificationStatue status;
}
