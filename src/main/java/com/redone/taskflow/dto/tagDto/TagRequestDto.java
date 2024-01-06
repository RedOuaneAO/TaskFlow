package com.redone.taskflow.dto.tagDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequestDto {
    @NotBlank(message = "The name of the tag cannot be Blank")
    private String name;
}
