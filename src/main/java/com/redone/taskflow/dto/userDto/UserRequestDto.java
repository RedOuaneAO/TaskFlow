package com.redone.taskflow.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @NotBlank(message = "Second Name cannot be blank")
    private String secondName;
    @NotBlank(message = "User Name cannot be blank")
    private String userName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min=5 , max = 8, message = "The Password size should be between 5 and 8 characters")
    private String password;
    @Email(message = "Please Enter a correct email ex(user@gmail.com)")
    private String email;
}
