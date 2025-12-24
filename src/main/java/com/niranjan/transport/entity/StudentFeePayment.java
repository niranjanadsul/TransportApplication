package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "student_fee_payment")
@Getter
@Setter
public class StudentFeePayment {

    /*Track paid vs pending fees
    Handle advance payments
    Record multiple payments in a month
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /*Multiple rows allowed for same student + feeMonth
    Pending amount is calculated later (not stored)*/
    // Month this payment is for (example: 2025-07-01 = July 2025)
    @Column(name = "fee_month", nullable = false)
    private LocalDate feeMonth;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(length = 100)
    private String remarks;
}
