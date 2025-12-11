package com.society;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SocietyManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocietyManagementApplication.class, args);
    }
    @RestController
    public class PublicController {
        @GetMapping("/")
        public String home() {
            return "Society Management API is running!";
        }
    }
}