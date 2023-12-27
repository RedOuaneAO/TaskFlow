package com.redone.taskflow.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String firstName;
    private String secondName;
    private String userName;
    private String email;
}
