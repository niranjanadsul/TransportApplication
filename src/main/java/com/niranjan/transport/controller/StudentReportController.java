package com.niranjan.transport.controller;

import com.niranjan.transport.dto.StudentFeeMatrixReport;
import com.niranjan.transport.service.StudentReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports/students")
public class StudentReportController {

    private final StudentReportService studentReportService;

    public StudentReportController(StudentReportService studentReportService) {
        this.studentReportService = studentReportService;
    }

    /**
     * Student Fee Matrix Report
     * Rows  -> Students
     * Columns -> Months
     * Cell -> Amount Paid
     */
    @GetMapping("/fee-matrix")
    public StudentFeeMatrixReport getStudentFeeMatrix(
            @RequestParam("startMonth") String startMonth,
            @RequestParam("endMonth") String endMonth) {

        YearMonth start = YearMonth.parse(startMonth);
        YearMonth end = YearMonth.parse(endMonth);

        return studentReportService.getStudentFeeMatrix(start, end);
    }
}
