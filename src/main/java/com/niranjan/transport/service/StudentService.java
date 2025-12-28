package com.niranjan.transport.service;

import com.niranjan.transport.dto.StudentPendingFeeDTO;
import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    void assignStudentToVehicle(
            Long studentId,
            Long vehicleId,
            LocalDate startDate
    );

    void changeStudentFee(
            Long studentId,
            BigDecimal newMonthlyFee,
            LocalDate effectiveFrom
    );

    void deactivateStudent(
            Long studentId,
            LocalDate endDate
    );

    BigDecimal calculatePendingFee(Long studentId);
    List<StudentPendingFeeDTO> getPendingFeeSummary();


}
