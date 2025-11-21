package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// NOTE: We remove the @Builder import since we are not using it.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    // 6 Fields to match the 6-argument constructor that AuthService will use
    private Long userId;
    private String email;
    private String role;
    private String name;
    private String message;
    private String token;
}
