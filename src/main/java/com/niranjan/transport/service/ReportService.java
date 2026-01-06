package com.niranjan.transport.service;

import com.niranjan.transport.dto.MonthlyProfitLossDTO;

public interface ReportService {

    MonthlyProfitLossDTO getMonthlyProfitLoss(int year, int month);
}
