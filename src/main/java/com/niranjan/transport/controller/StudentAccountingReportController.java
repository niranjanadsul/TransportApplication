package com.niranjan.transport.controller;

import com.niranjan.transport.dto.StudentFeeAccountingRow;
import com.niranjan.transport.service.StudentAccountingReportService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/reports/students")
public class StudentAccountingReportController {

    private final StudentAccountingReportService service;

    public StudentAccountingReportController(
            StudentAccountingReportService service) {
        this.service = service;
    }

    @GetMapping("/accounting")
    public List<StudentFeeAccountingRow> getAccountingReport(
            @RequestParam YearMonth startMonth,
            @RequestParam YearMonth endMonth) {

        return service.getStudentAccountingReport(startMonth, endMonth);
    }
}
