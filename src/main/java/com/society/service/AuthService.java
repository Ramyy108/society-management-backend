package com.society.service;

import com.society.dto.AuthResponse;
import com.society.dto.LoginRequest;
import com.society.dto.RegisterRequest;
import com.society.entity.User;
import com.society.repository.UserRepository;
import com.society.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse registerOwner(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(User.Role.OWNER);

        userRepository.save(user);

        String token = "fake-token-" + user.getId() + "-OWNER";


        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getName(),
                "User registered successfully",
                token
        );
    }

    @Transactional
    public AuthResponse registerAdmin(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(User.Role.ADMIN);

        userRepository.save(user);

        String token = "fake-token-" + user.getId() + "-ADMIN";


        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getName(),
                "Admin registered successfully",
                token
        );
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found after successful authentication."));

        String roleString = user.getRole().name();
        String fakeToken = "fake-token-" + user.getId() + "-" + roleString;


        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getName(),
                "Login successful",
                fakeToken
        );
    }
}