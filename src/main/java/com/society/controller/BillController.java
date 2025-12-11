package com.society.controller;
import com.society.dto.BillDTO;
import com.society.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    // Endpoint: POST /api/bills (to generate bills)
    @PostMapping
    public ResponseEntity<Map<String, String>> generateBills(@RequestBody BillDTO billDTO) {

        billService.generateMonthlyBills(billDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Bills generated successfully"));
    }
    // Endpoint: GET /api/bills (Admin view all bills)
    @GetMapping
    public ResponseEntity<List<BillDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }
    // Endpoint: GET /api/bills/owner
    @GetMapping("/owner")
    public ResponseEntity<List<BillDTO>> getOwnerBills() {
        return ResponseEntity.ok(billService.getOwnerBills());
    }
    // Endpoint: PUT /api/bills/{id}/pay
    @PutMapping("/{id}/pay")
    public ResponseEntity<BillDTO> payBill(@PathVariable Long id) {
        BillDTO updatedBill = billService.markBillAsPaid(id);
        return ResponseEntity.ok(updatedBill);
    }
}