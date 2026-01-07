package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public class DriverRunningBalanceRowDTO {

    private Long driverId;
    private String driverName;
    private YearMonth month;

    private BigDecimal monthlyNet;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;

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

    public BigDecimal getMonthlyNet() {
        return monthlyNet;
    }

    public void setMonthlyNet(BigDecimal monthlyNet) {
        this.monthlyNet = monthlyNet;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }
}
