package com.niranjan.transport.dto;

import java.math.BigDecimal;

public class DriverMonthlySummaryDTO {

    private Long driverId;
    private int year;
    private int month;

    private BigDecimal totalSalaryPaid;
    private BigDecimal totalTourPaid;
    private BigDecimal totalAdvanceTaken;

    public DriverMonthlySummaryDTO(
            Long driverId,
            int year,
            int month,
            BigDecimal totalSalaryPaid,
            BigDecimal totalTourPaid,
            BigDecimal totalAdvanceTaken) {

        this.driverId = driverId;
        this.year = year;
        this.month = month;
        this.totalSalaryPaid = totalSalaryPaid;
        this.totalTourPaid = totalTourPaid;
        this.totalAdvanceTaken = totalAdvanceTaken;
    }

    public BigDecimal getNetPaid() {
        return totalSalaryPaid
                .add(totalTourPaid)
                .subtract(totalAdvanceTaken);
    }

    public Long getDriverId() {
        return driverId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getTotalSalaryPaid() {
        return totalSalaryPaid;
    }

    public BigDecimal getTotalTourPaid() {
        return totalTourPaid;
    }

    public BigDecimal getTotalAdvanceTaken() {
        return totalAdvanceTaken;
    }
}
