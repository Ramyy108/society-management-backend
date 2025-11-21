package com.society.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List; // Import List for getAuthorities return type

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // The hashed password

    @Column(nullable = false)
    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Flat flat;

    // --- UserDetails Implementations ---

    /**
     * FIX: This method must return the stored hashed password.
     * It is called by the AuthenticationProvider to verify credentials.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the user's role/authorities required by Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a list containing the user's role prefixed with "ROLE_"
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Returns the field used as the username (which is the email in your case).
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    // --- Standard Methods (usually return true) ---

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // --- Enum for Role ---
    public enum Role {
        ADMIN, OWNER
    }
}