package com.society.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String phone;
    private String flatNumber;
    private String block;
    private Integer floor;
}