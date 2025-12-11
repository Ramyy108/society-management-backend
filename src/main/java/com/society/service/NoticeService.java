package com.society.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.society.dto.NoticeDTO;
import com.society.entity.Notice;
import com.society.exception.ResourceNotFoundException;
import com.society.repository.NoticeRepository;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }
    @Transactional
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        if (noticeDTO == null) {
            throw new IllegalArgumentException("noticeDTO is null");
        }
        Notice notice = new Notice();
        notice.setTitle(noticeDTO.getTitle());
        notice.setDescription(noticeDTO.getDescription());
        notice = noticeRepository.save(notice);
        return convertToDTO(notice);
    }
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    @Transactional
    public NoticeDTO updateNotice(Long id, NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notice not found"));
        notice.setTitle(noticeDTO.getTitle());
        notice.setDescription(noticeDTO.getDescription());
        notice = noticeRepository.save(notice);
        return convertToDTO(notice);
    }
    @Transactional
    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notice not found");
        }
        noticeRepository.deleteById(id);
    }
    private NoticeDTO convertToDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setDescription(notice.getDescription());
        dto.setCreatedAt(notice.getCreatedAt());
        dto.setUpdatedAt(notice.getUpdatedAt());
        return dto;
    }
}