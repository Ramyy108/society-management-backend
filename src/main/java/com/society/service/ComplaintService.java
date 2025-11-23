package com.society.service;

import com.society.dto.ComplaintDTO;
import com.society.entity.Complaint;
import com.society.entity.User;
import com.society.exception.ResourceNotFoundException;
import com.society.repository.ComplaintRepository;
import com.society.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintDTO createComplaint(ComplaintDTO complaintDTO) {


        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in security context"));

        Complaint complaint = new Complaint();
        complaint.setOwner(owner);
        complaint.setTitle(complaintDTO.getTitle());
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setStatus(Complaint.Status.PENDING);
        complaint.setCreatedAt(LocalDateTime.now());

        complaint = complaintRepository.save(complaint);
        return convertToDTO(complaint);
    }

    public List<ComplaintDTO> getAllComplaints() {
        return complaintRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ComplaintDTO resolveComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        complaint.setStatus(Complaint.Status.RESOLVED);
        complaint.setResolvedAt(LocalDateTime.now());
        complaint = complaintRepository.save(complaint);
        return convertToDTO(complaint);
    }

    private ComplaintDTO convertToDTO(Complaint complaint) {
        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setOwnerName(complaint.getOwner().getName());
        dto.setFlatNumber(complaint.getOwner().getFlat() != null ?
                complaint.getOwner().getFlat().getFlatNumber() : "N/A");
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setStatus(complaint.getStatus().name());
        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setResolvedAt(complaint.getResolvedAt());
        return dto;
    }
}