package com.niranjan.transport.dto;

import java.time.YearMonth;
import java.util.List;

public class DriverMonthlyAccountReport {

    private List<YearMonth> months;
    private List<DriverMonthlyAccountRowDTO> rows;

    // getters & setters

    public List<YearMonth> getMonths() {
        return months;
    }

    public void setMonths(List<YearMonth> months) {
        this.months = months;
    }

    public List<DriverMonthlyAccountRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<DriverMonthlyAccountRowDTO> rows) {
        this.rows = rows;
    }
}
