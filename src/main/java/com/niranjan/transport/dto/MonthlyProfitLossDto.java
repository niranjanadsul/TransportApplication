package com.niranjan.transport.dto;

import java.math.BigDecimal;

public class MonthlyProfitLossDto {

    private int year;
    private int month;

    private BigDecimal studentIncome;
    private BigDecimal tourIncome;
    private BigDecimal driverExpense;
    private BigDecimal vehicleExpense;

    public MonthlyProfitLossDto(
            int year,
            int month,
            BigDecimal studentIncome,
            BigDecimal tourIncome,
            BigDecimal driverExpense,
            BigDecimal vehicleExpense) {

        this.year = year;
        this.month = month;
        this.studentIncome = studentIncome;
        this.tourIncome = tourIncome;
        this.driverExpense = driverExpense;
        this.vehicleExpense = vehicleExpense;
    }

    public BigDecimal getTotalIncome() {
        return studentIncome.add(tourIncome);
    }

    public BigDecimal getTotalExpense() {
        return driverExpense.add(vehicleExpense);
    }

    public BigDecimal getNetProfitOrLoss() {
        return getTotalIncome().subtract(getTotalExpense());
    }

    // getters only
}
