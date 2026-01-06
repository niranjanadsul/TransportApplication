package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public class DriverMonthlyAccountRowDTO {

    private Long driverId;
    private String driverName;
    private YearMonth month;

    private BigDecimal salaryPaid;
    private BigDecimal advanceTaken;
    private BigDecimal tourPayments;
    private BigDecimal adjustments;
    private BigDecimal netAmount;

    // getters & setters

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public BigDecimal getSalaryPaid() {
        return salaryPaid;
    }

    public void setSalaryPaid(BigDecimal salaryPaid) {
        this.salaryPaid = salaryPaid;
    }

    public BigDecimal getAdvanceTaken() {
        return advanceTaken;
    }

    public void setAdvanceTaken(BigDecimal advanceTaken) {
        this.advanceTaken = advanceTaken;
    }

    public BigDecimal getTourPayments() {
        return tourPayments;
    }

    public void setTourPayments(BigDecimal tourPayments) {
        this.tourPayments = tourPayments;
    }

    public BigDecimal getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(BigDecimal adjustments) {
        this.adjustments = adjustments;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }
}
