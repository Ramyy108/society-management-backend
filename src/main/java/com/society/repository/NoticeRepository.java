package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();
}