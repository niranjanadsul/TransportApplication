package com.niranjan.transport.service;

import com.niranjan.transport.dto.MonthlyProfitLossDto;

public interface ReportService {

    MonthlyProfitLossDto getMonthlyProfitLoss(int year, int month);
}
