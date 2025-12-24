package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "student_fee_plan")
@Getter
@Setter
public class StudentFeePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private BigDecimal monthlyFee;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /*endDate = NULL → current active fee
    Only one active fee plan per student (enforced later)*/
    @Column(name = "end_date")
    private LocalDate endDate;
}
