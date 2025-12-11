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
        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(tokenPrefix.length());
        String[] parts = token.split("-");
        if (parts.length != 2) { // Expected format: ID-EMAIL
            filterChain.doFilter(request, response);
            return;
        }
        final String TEST_EMAIL = "fresh.test.owner@example.com";
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(TEST_EMAIL);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}