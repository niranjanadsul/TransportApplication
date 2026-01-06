package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourTripAccountingDTO {

    private Long tripId;
    private String customerName;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    private BigDecimal tripAmount;
    private BigDecimal totalPaid;
    private BigDecimal pendingAmount;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(LocalDate tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public LocalDate getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(LocalDate tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public BigDecimal getTripAmount() {
        return tripAmount;
    }

    public void setTripAmount(BigDecimal tripAmount) {
        this.tripAmount = tripAmount;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }
}
