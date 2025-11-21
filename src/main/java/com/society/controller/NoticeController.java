package com.society.controller;

import com.society.dto.NoticeDTO;
import com.society.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    
    // <-- do NOT initialize to null, let Lombok generate constructor for injection
    private final NoticeService noticeService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeDTO> createNotice(@Valid @RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeService.createNotice(noticeDTO));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
    
    @GetMapping("/mine")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<NoticeDTO>> getOwnerNotices() {
        // If you implement owner-specific fetching later, replace this call.
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeDTO> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.ok(noticeService.updateNotice(id, noticeDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
