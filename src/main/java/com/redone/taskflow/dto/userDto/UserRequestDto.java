package com.redone.taskflow.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    private String firstName;
    private String secondName;
    private String userName;
    private String password;
    private String email;
}
