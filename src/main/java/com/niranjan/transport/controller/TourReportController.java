package com.niranjan.transport.controller;

import com.niranjan.transport.dto.TourPaymentMatrixReport;
import com.niranjan.transport.service.TourReportService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports/tours")
public class TourReportController {

    private final TourReportService tourReportService;

    public TourReportController(TourReportService tourReportService) {
        this.tourReportService = tourReportService;
    }

    /**
     * Tour Payment Matrix Report
     * Rows -> Tour Trips
     * Columns -> Months
     * Cell -> Amount Paid
     */
    @GetMapping("/payment-matrix")
    public TourPaymentMatrixReport getTourPaymentMatrix(
            @RequestParam String startMonth,
            @RequestParam String endMonth) {

        return tourReportService.getTourPaymentMatrix(
                YearMonth.parse(startMonth),
                YearMonth.parse(endMonth)
        );
    }
}
