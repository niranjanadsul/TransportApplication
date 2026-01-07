package com.niranjan.transport.controller;

import com.niranjan.transport.dto.DriverRunningBalanceReport;
import com.niranjan.transport.service.DriverRunningBalanceService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports/drivers")
public class DriverRunningBalanceController {

    private final DriverRunningBalanceService service;

    public DriverRunningBalanceController(
            DriverRunningBalanceService service) {
        this.service = service;
    }

    @GetMapping("/running-balance")
    public DriverRunningBalanceReport getRunningBalance(
            @RequestParam String startMonth,
            @RequestParam String endMonth) {

        return service.getRunningBalanceReport(
                YearMonth.parse(startMonth),
                YearMonth.parse(endMonth)
        );
    }
}
