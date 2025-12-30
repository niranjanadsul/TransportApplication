package com.niranjan.transport.dto;

import java.time.YearMonth;
import java.util.List;

public class StudentFeeMatrixReport {

    private List<YearMonth> months;
    private List<StudentFeeMatrixRow> rows;

    public List<YearMonth> getMonths() {
        return months;
    }

    public void setMonths(List<YearMonth> months) {
        this.months = months;
    }

    public List<StudentFeeMatrixRow> getRows() {
        return rows;
    }

    public void setRows(List<StudentFeeMatrixRow> rows) {
        this.rows = rows;
    }
}
