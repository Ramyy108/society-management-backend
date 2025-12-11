package com.society.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.society.entity.Complaint;
import com.society.entity.User;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByOwnerOrderByCreatedAtDesc(User owner);
    List<Complaint> findAllByOrderByCreatedAtDesc();
}