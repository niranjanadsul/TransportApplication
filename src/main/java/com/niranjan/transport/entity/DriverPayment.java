package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "driver_payment")
@Getter
@Setter
public class DriverPayment {
    /*A driver payment:
    Always belongs to a Driver
    May optionally belong to a TourTrip
    Has a type to distinguish salary / advance / tour pay
    Is never mixed with vehicle expenses*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverPaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "tour_trip_id")
    private TourTrip tourTrip;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(length = 150)
    private String remarks;
}
