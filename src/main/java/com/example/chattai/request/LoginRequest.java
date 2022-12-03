package com.example.chattai.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email cannot blank")
    @Email(message = "Invalid email")
    private String email;
    private String password;
}
