package com.EventManagementSystem.EventManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private Long id; // Ensure this property exists


    @NotEmpty
    @Size(min = 2,message ="Username must be minimum of 2 character")
    private String userName;

    @Email(message ="Email is not valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String role;
}
