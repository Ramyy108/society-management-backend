package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Bill;
import com.society.entity.User;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Get all bills for a specific owner, ordered by creation date (descending)
    List<Bill> findByOwnerOrderByCreatedAtDesc(User owner);

    // Get all bills in the system, ordered by creation date (descending)
    List<Bill> findAllByOrderByCreatedAtDesc();

    // Check if a bill already exists for a given month and year
    boolean existsByMonthAndYear(String month, Integer year);
}