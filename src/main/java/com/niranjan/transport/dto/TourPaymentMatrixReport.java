package com.niranjan.transport.dto;

import java.time.YearMonth;
import java.util.List;

public class TourPaymentMatrixReport {

    private List<YearMonth> months;
    private List<TourPaymentMatrixRow> rows;

    public List<YearMonth> getMonths() {
        return months;
    }

    public void setMonths(List<YearMonth> months) {
        this.months = months;
    }

    public List<TourPaymentMatrixRow> getRows() {
        return rows;
    }

    public void setRows(List<TourPaymentMatrixRow> rows) {
        this.rows = rows;
    }
}
