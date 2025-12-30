package com.niranjan.transport.service;

import com.niranjan.transport.dto.StudentFeeMatrixReport;
import com.niranjan.transport.dto.StudentFeeMatrixRow;
import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentFeePayment;
import com.niranjan.transport.repository.StudentFeePaymentRepository;
import com.niranjan.transport.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class StudentReportService {

    private final StudentRepository studentRepository;
    private final StudentFeePaymentRepository paymentRepository;

    public StudentReportService(
            StudentRepository studentRepository,
            StudentFeePaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    // ==============================
    // PUBLIC REPORT METHOD
    // ==============================
    public StudentFeeMatrixReport getStudentFeeMatrix(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<YearMonth> months = generateMonths(startMonth, endMonth);
        List<Student> students = studentRepository.findAll();

        List<StudentFeeMatrixRow> rows = new ArrayList<>();

        for (Student student : students) {

            StudentFeeMatrixRow row = new StudentFeeMatrixRow();
            row.setStudentId(student.getId());
            row.setStudentName(student.getName());

            Map<YearMonth, BigDecimal> monthMap = initializeMonthMap(months);

            for (YearMonth month : months) {

                LocalDate feeMonthDate = month.atDay(1);

                List<StudentFeePayment> payments =
                        paymentRepository.findByStudentAndFeeMonth(
                                student,
                                feeMonthDate
                        );

                BigDecimal totalPaid = payments.stream()
                        .map(StudentFeePayment::getAmountPaid)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                monthMap.put(month, totalPaid);
            }

            row.setMonthlyPayments(monthMap);
            rows.add(row);
        }

        StudentFeeMatrixReport report = new StudentFeeMatrixReport();
        report.setMonths(months);
        report.setRows(rows);

        return report;
    }

    // ==============================
    // HELPER METHODS
    // ==============================

    private List<YearMonth> generateMonths(
            YearMonth start,
            YearMonth end) {

        List<YearMonth> months = new ArrayList<>();
        YearMonth current = start;

        while (!current.isAfter(end)) {
            months.add(current);
            current = current.plusMonths(1);
        }
        return months;
    }

    private Map<YearMonth, BigDecimal> initializeMonthMap(
            List<YearMonth> months) {

        Map<YearMonth, BigDecimal> map = new LinkedHashMap<>();
        for (YearMonth month : months) {
            map.put(month, BigDecimal.ZERO);
        }
        return map;
    }
}
