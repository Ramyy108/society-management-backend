package com.society.controller;

import com.society.dto.ComplaintDTO;
import com.society.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor // Lombok will generate a constructor for 'complaintService'
public class ComplaintController {

    // FIX 1: Correctly define the final service field.
    // The @RequiredArgsConstructor handles the injection, preventing the NPE.
    private final ComplaintService complaintService;


    @PostMapping
    // FIX 2: Removed the problematic @PreAuthorize("hasRole('OWNER')").
    // Access control is now managed solely by SecurityConfig.java, which is set to permitAll() for testing.
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        // You should eventually get the current user's ID here from the SecurityContext
        // For now, we assume the ComplaintService handles the relationship.
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(complaintDTO));
    }

    @GetMapping
    // Note: You should remove or comment out any @PreAuthorize on other methods too
    // if you want to test them before implementing full security.
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<ComplaintDTO> resolveComplaint(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.resolveComplaint(id));
    }
}
