package com.society.dto;

import java.time.LocalDateTime;

public class ComplaintDTO {
    private Long id;
    private String ownerName;
    private String flatNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public ComplaintDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}
