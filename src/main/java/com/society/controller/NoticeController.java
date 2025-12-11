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
    private final NoticeService noticeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NoticeDTO> createNotice(@Valid @RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeService.createNotice(noticeDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<NoticeDTO>> getOwnerNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NoticeDTO> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.ok(noticeService.updateNotice(id, noticeDTO));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
