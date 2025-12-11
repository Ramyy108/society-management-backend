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
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;
    @PostMapping
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(complaintDTO));
    }
    @GetMapping
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }
    @PutMapping("/{id}/resolve")
    public ResponseEntity<ComplaintDTO> resolveComplaint(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.resolveComplaint(id));
    }
}
