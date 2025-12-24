package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentFeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StudentFeePaymentRepository
        extends JpaRepository<StudentFeePayment, Long> {

    List<StudentFeePayment> findByStudentAndFeeMonth(Student student, LocalDate feeMonth);
}
