package com.niranjan.transport.dto;

import java.math.BigDecimal;

public class StudentPendingFeeDTO {
    /*
    *   This works for:
        Active students
        Students who left but still owe money*/
    private Long studentId;
    private String studentName;
    private String vehicleNumber;
    private BigDecimal pendingAmount;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }
}
