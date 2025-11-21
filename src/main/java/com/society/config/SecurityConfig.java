package com.society.config;

import com.society.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import this

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Core Security Beans ---

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ADDED: Define the FakeTokenFilter as a bean
    @Bean
    public FakeTokenFilter fakeTokenFilter() {
        return new FakeTokenFilter(userDetailsService);
    }

    // --- Security Filter Chain ---

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // PUBLIC ACCESS: Login, Register, Root Path
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/").permitAll()

                        // REVERTED RULES: Endpoints are now SECURE and check roles
                        // OWNERs can create complaints
                        .requestMatchers(HttpMethod.POST, "/api/complaints").hasRole("OWNER")
                        // All logged-in users can view complaints
                        .requestMatchers(HttpMethod.GET, "/api/complaints").authenticated()
                        // Only ADMINs can resolve complaints
                        .requestMatchers(HttpMethod.PUT, "/api/complaints/**").hasRole("ADMIN")

                        // All other requests MUST be authenticated
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        // ADDED: Register the filter to run BEFORE the standard authentication checks
        http.addFilterBefore(fakeTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}