package com.niranjan.transport.dto;

import java.time.YearMonth;
import java.util.List;

public class VehicleMonthlyFinanceReport {

    private List<YearMonth> months;
    private List<VehicleMonthlyFinanceRow> rows;

    public List<YearMonth> getMonths() {
        return months;
    }

    public void setMonths(List<YearMonth> months) {
        this.months = months;
    }

    public List<VehicleMonthlyFinanceRow> getRows() {
        return rows;
    }

    public void setRows(List<VehicleMonthlyFinanceRow> rows) {
        this.rows = rows;
    }
}
