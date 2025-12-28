package com.niranjan.transport.service.impl;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentFeePayment;
import com.niranjan.transport.repository.StudentFeePaymentRepository;
import com.niranjan.transport.repository.StudentRepository;
import com.niranjan.transport.service.StudentFeePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class StudentFeePaymentServiceImpl
        implements StudentFeePaymentService {

    private final StudentRepository studentRepository;
    private final StudentFeePaymentRepository paymentRepository;

    public StudentFeePaymentServiceImpl(
            StudentRepository studentRepository,
            StudentFeePaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void recordPayment(
            Long studentId,
            BigDecimal amount,
            LocalDate paymentDate,
            String remarks) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentFeePayment payment = new StudentFeePayment();
        payment.setStudent(student);
        payment.setAmountPaid(amount);
        payment.setPaymentDate(paymentDate);
        payment.setRemarks(remarks);

        paymentRepository.save(payment);
    }
}
