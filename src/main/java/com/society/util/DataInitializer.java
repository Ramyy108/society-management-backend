package com.society.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.society.entity.Flat;
import com.society.entity.User;
import com.society.repository.FlatRepository;
import com.society.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;
    private final FlatRepository flatRepository;
    private final PasswordEncoder passwordEncoder;
    public DataInitializer(UserRepository userRepository,
                           FlatRepository flatRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        try {
            // Admin
            if (!userRepository.existsByEmail("admin@example.com")) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setName("Admin User");
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                log.info("Admin user created: admin@example.com");
            } else {
                log.info("Admin user already exists");
            }

            // Sample owner + flat
            if (!userRepository.existsByEmail("owner@example.com")) {
                User owner = new User();
                owner.setEmail("owner@example.com");
                owner.setPassword(passwordEncoder.encode("owner123"));
                owner.setName("John Doe");
                owner.setPhone("9876543210");
                owner.setRole(User.Role.OWNER);
                owner = userRepository.save(owner);

                Flat flat = new Flat();
                flat.setFlatNumber("A-101");
                flat.setBlock("A");
                flat.setFloor(1);
                flat.setOwner(owner);
                flatRepository.save(flat);
                log.info("Sample owner + flat created: owner@example.com / A-101");
            } else {
                log.info("Sample owner already exists");
            }
        } catch (Exception e) {
            log.error("DataInitializer error: {}", e.getMessage(), e);
        }
    }
}