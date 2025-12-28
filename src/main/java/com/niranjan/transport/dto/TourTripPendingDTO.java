package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourTripPendingDTO {

    private Long tripId;
    private String customerName;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private BigDecimal tripAmount;
    private BigDecimal pendingAmount;

    public TourTripPendingDTO(
            Long tripId,
            String customerName,
            LocalDate tripStartDate,
            LocalDate tripEndDate,
            BigDecimal tripAmount,
            BigDecimal pendingAmount) {
        this.tripId = tripId;
        this.customerName = customerName;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripAmount = tripAmount;
        this.pendingAmount = pendingAmount;
    }

    public Long getTripId() {
        return tripId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getTripStartDate() {
        return tripStartDate;
    }

    public LocalDate getTripEndDate() {
        return tripEndDate;
    }

    public BigDecimal getTripAmount() {
        return tripAmount;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }
}
