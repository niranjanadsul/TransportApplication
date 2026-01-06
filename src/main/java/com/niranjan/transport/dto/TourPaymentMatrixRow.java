package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

public class TourPaymentMatrixRow {

    private Long tourTripId;
    private String customerName;
    private Map<YearMonth, BigDecimal> monthlyPayments;

    public Long getTourTripId() {
        return tourTripId;
    }

    public void setTourTripId(Long tourTripId) {
        this.tourTripId = tourTripId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<YearMonth, BigDecimal> getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setMonthlyPayments(Map<YearMonth, BigDecimal> monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }
}
