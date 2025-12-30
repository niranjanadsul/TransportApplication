package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

public class StudentFeeMatrixRow {

    private Long studentId;
    private String studentName;
    private Map<YearMonth, BigDecimal> monthlyPayments;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Map<YearMonth, BigDecimal> getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setMonthlyPayments(Map<YearMonth, BigDecimal> monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }
}
