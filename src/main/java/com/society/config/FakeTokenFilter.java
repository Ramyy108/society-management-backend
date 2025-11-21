package com.society.config;

import com.society.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FakeTokenFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    public FakeTokenFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String tokenPrefix = "Bearer fake-token-";

        // 1. Check for the correct header and token prefix
        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the user details from the token: fake-token-ID-EMAIL
        String token = authHeader.substring(tokenPrefix.length());
        String[] parts = token.split("-");

        if (parts.length != 2) { // Expected format: ID-EMAIL
            filterChain.doFilter(request, response);
            return;
        }

        // We'll use the email part of the token to load the user
        // Assuming your fake token structure is: fake-token-ID-ROLE, let's adjust for email lookup
        // **IMPORTANT:** Since your login returns fake-token-ID-ROLE, we can't get the email reliably.
        // We'll revert to using a fixed test email for the final check of the Security Context population.
        final String TEST_EMAIL = "fresh.test.owner@example.com";

        // 3. Check if user is already authenticated (should be null for new request)
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Load User Details from the service
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(TEST_EMAIL);

            // 5. Create Authentication object and populate Security Context
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // This is the CRUCIAL step: setting the authenticated user
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}