package com.niranjan.transport.dto;

import java.util.List;

public class DriverRunningBalanceReport {

    private List<DriverRunningBalanceRowDTO> rows;

    public List<DriverRunningBalanceRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<DriverRunningBalanceRowDTO> rows) {
        this.rows = rows;
    }
}
