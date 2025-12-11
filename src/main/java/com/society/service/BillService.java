package com.society.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.society.dto.BillDTO;
import com.society.entity.Bill;
import com.society.entity.User;
import com.society.repository.BillRepository;
import com.society.repository.UserRepository;
import com.society.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    @Transactional
    public void generateMonthlyBills(BillDTO billInput) {
        String month = billInput.getMonth();
        Integer year = billInput.getYear();
        BigDecimal amount = billInput.getAmount();
        LocalDate dueDate = billInput.getDueDate();
        if (month == null || year == null || amount == null || dueDate == null) {
            throw new IllegalArgumentException("Month, Year, Amount, and Due Date are required.");
        }
        List<User> owners = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == User.Role.OWNER)
                .collect(Collectors.toList());
        for (User owner : owners) {
            Bill bill = new Bill();
            bill.setOwner(owner);
            bill.setAmount(amount);
            bill.setMonth(month);
            bill.setYear(year);
            bill.setStatus(Bill.Status.PENDING);
            bill.setDueDate(dueDate);
            billRepository.save(bill);
        }
    }
    public List<BillDTO> getOwnerBills() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found in security context"));
        return billRepository.findByOwnerOrderByCreatedAtDesc(owner)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<BillDTO> getAllBills() {
        return billRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BillDTO markBillAsPaid(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));
        if (bill.getStatus() == Bill.Status.PAID) {
            throw new RuntimeException("Bill is already paid.");
        }
        bill.setStatus(Bill.Status.PAID);
        bill.setPaymentDate(LocalDateTime.now());
        bill = billRepository.save(bill);
        return convertToDTO(bill);
    }

    private BillDTO convertToDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        if (bill.getOwner() != null) {
            dto.setOwnerName(bill.getOwner().getName());
            dto.setFlatNumber(bill.getOwner().getFlat() != null ?
                    bill.getOwner().getFlat().getFlatNumber() : "N/A");
        }
        dto.setAmount(bill.getAmount());
        dto.setMonth(bill.getMonth());
        dto.setYear(bill.getYear());
        dto.setDueDate(bill.getDueDate());
        dto.setStatus(bill.getStatus() != null ? bill.getStatus().name() : Bill.Status.PENDING.name());
        dto.setPaymentDate(bill.getPaymentDate());
        return dto;
    }
}