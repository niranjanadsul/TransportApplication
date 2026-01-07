package com.niranjan.transport.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DriverSettlementRequestDTO {

    private Long driverId;

    /*
     * Positive amount  -> company pays driver
     * Negative amount  -> driver returns money
     */
    private BigDecimal settlementAmount;

    private LocalDate settlementDate;
    private String remarks;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
