package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tour_trip")
@Getter
@Setter
public class TourTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "trip_start_date", nullable = false)
    private LocalDate tripStartDate;

    @Column(name = "trip_end_date")
    private LocalDate tripEndDate;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "estimated_amount")
    private BigDecimal estimatedAmount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;

    @Column
    private String remarks;
}
