package com.niranjan.transport.controller;

import com.niranjan.transport.dto.VehicleMonthlyFinanceReport;
import com.niranjan.transport.service.VehicleReportService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports/vehicles")
public class VehicleReportController {

    private final VehicleReportService vehicleReportService;

    public VehicleReportController(VehicleReportService vehicleReportService) {
        this.vehicleReportService = vehicleReportService;
    }

    /**
     * Vehicle Monthly Financial Matrix
     * Rows -> Vehicle
     * SubRows -> School Income, Tour Income, Expense
     * Columns -> Months
     */
    @GetMapping("/monthly-finance")
    public VehicleMonthlyFinanceReport getVehicleMonthlyFinance(
            @RequestParam String startMonth,
            @RequestParam String endMonth) {

        return vehicleReportService.getVehicleMonthlyFinance(
                YearMonth.parse(startMonth),
                YearMonth.parse(endMonth)
        );
    }
}
