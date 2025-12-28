package com.niranjan.transport.dto;

import java.math.BigDecimal;

public class VehicleMonthlyExpenseDTO {

    private Long vehicleId;
    private int year;
    private int month;

    private BigDecimal fuel;
    private BigDecimal repair;
    private BigDecimal fine;
    private BigDecimal toll;
    private BigDecimal other;

    public VehicleMonthlyExpenseDTO(
            Long vehicleId,
            int year,
            int month,
            BigDecimal fuel,
            BigDecimal repair,
            BigDecimal fine,
            BigDecimal toll,
            BigDecimal other) {

        this.vehicleId = vehicleId;
        this.year = year;
        this.month = month;
        this.fuel = fuel;
        this.repair = repair;
        this.fine = fine;
        this.toll = toll;
        this.other = other;
    }

    public BigDecimal getTotalExpense() {
        return fuel
                .add(repair)
                .add(fine)
                .add(toll)
                .add(other);
    }

    // getters only
}
