package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Bill;
import com.society.entity.User;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {


    List<Bill> findByOwnerOrderByCreatedAtDesc(User owner);


    List<Bill> findAllByOrderByCreatedAtDesc();


    boolean existsByMonthAndYear(String month, Integer year);
}