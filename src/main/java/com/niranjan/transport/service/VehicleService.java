package com.niranjan.transport.service;

import com.niranjan.transport.dto.VehicleMonthlyExpenseDTO;

public interface VehicleService {

    VehicleMonthlyExpenseDTO getMonthlyExpenseSummary(
            Long vehicleId,
            int year,
            int month);
}
