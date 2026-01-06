package com.niranjan.transport.service;

import com.niranjan.transport.dto.StudentFeeAccountingRow;
import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentFeePayment;
import com.niranjan.transport.entity.StudentFeePlan;
import com.niranjan.transport.repository.StudentFeePaymentRepository;
import com.niranjan.transport.repository.StudentFeePlanRepository;
import com.niranjan.transport.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentAccountingReportService {

    private final StudentRepository studentRepository;
    private final StudentFeePlanRepository feePlanRepository;
    private final StudentFeePaymentRepository paymentRepository;

    public StudentAccountingReportService(
            StudentRepository studentRepository,
            StudentFeePlanRepository feePlanRepository,
            StudentFeePaymentRepository paymentRepository) {

        this.studentRepository = studentRepository;
        this.feePlanRepository = feePlanRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<StudentFeeAccountingRow> getStudentAccountingReport(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<StudentFeeAccountingRow> result = new ArrayList<>();
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {

            List<StudentFeePlan> plans =
                    feePlanRepository.findByStudent(student);

            YearMonth current = startMonth;

            while (!current.isAfter(endMonth)) {

                BigDecimal expected = calculateExpected(plans, current);
                BigDecimal paid = calculatePaid(student, current);
                BigDecimal pending = expected.subtract(paid);

                StudentFeeAccountingRow row = new StudentFeeAccountingRow();
                row.setStudentId(student.getId());
                row.setStudentName(student.getName());
                row.setMonth(current);
                row.setExpectedAmount(expected);
                row.setPaidAmount(paid);
                row.setPendingAmount(pending);

                result.add(row);

                current = current.plusMonths(1);
            }
        }

        return result;
    }

    // -------------------------
    // Helper methods
    // -------------------------

    private BigDecimal calculateExpected(
            List<StudentFeePlan> plans,
            YearMonth month) {

        BigDecimal expected = BigDecimal.ZERO;

        for (StudentFeePlan plan : plans) {

            YearMonth start = YearMonth.from(plan.getStartDate());
            YearMonth end = plan.getEndDate() == null
                    ? YearMonth.now()
                    : YearMonth.from(plan.getEndDate());

            if (!month.isBefore(start) && !month.isAfter(end)) {
                expected = expected.add(plan.getMonthlyFee());
            }
        }
        return expected;
    }

    private BigDecimal calculatePaid(
            Student student,
            YearMonth month) {

        LocalDate feeMonth = month.atDay(1);

        List<StudentFeePayment> payments =
                paymentRepository.findByStudentAndFeeMonth(
                        student, feeMonth);

        BigDecimal paid = BigDecimal.ZERO;
        for (StudentFeePayment payment : payments) {
            paid = paid.add(payment.getAmountPaid());
        }
        return paid;
    }
}
