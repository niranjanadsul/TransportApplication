package com.niranjan.transport.controller;

import com.niranjan.transport.dto.DriverSettlementRequestDTO;
import com.niranjan.transport.service.DriverSettlementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverSettlementController {

    private final DriverSettlementService settlementService;

    public DriverSettlementController(
            DriverSettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @PostMapping("/settle")
    public void settleDriver(@RequestBody DriverSettlementRequestDTO request) {
        settlementService.settleDriverAccount(request);
    }
}
