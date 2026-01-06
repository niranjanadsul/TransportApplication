package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

public class VehicleMonthlyFinanceRow {

    private Long vehicleId;
    private String vehicleNumber;

    private Map<YearMonth, BigDecimal> schoolIncome;
    private Map<YearMonth, BigDecimal> tourIncome;
    private Map<YearMonth, BigDecimal> expenses;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Map<YearMonth, BigDecimal> getSchoolIncome() {
        return schoolIncome;
    }

    public void setSchoolIncome(Map<YearMonth, BigDecimal> schoolIncome) {
        this.schoolIncome = schoolIncome;
    }

    public Map<YearMonth, BigDecimal> getTourIncome() {
        return tourIncome;
    }

    public void setTourIncome(Map<YearMonth, BigDecimal> tourIncome) {
        this.tourIncome = tourIncome;
    }

    public Map<YearMonth, BigDecimal> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<YearMonth, BigDecimal> expenses) {
        this.expenses = expenses;
    }
}
