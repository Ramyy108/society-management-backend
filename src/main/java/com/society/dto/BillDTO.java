package com.society.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

    private Long id;
    private String ownerName;
    private String flatNumber;

    private BigDecimal amount;
    private String month;
    private Integer year;

    private LocalDate dueDate;
    private String status;
    private LocalDateTime paymentDate;


    private String ownerEmail;
}

