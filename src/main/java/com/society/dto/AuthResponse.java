package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {


    private Long userId;
    private String email;
    private String role;
    private String name;
    private String message;
    private String token;
}
