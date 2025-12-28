package com.niranjan.transport.service;

import com.niranjan.transport.dto.DriverMonthlySummaryDTO;

public interface DriverService {

    DriverMonthlySummaryDTO getMonthlyPaymentSummary(
            Long driverId,
            int year,
            int month);
}

