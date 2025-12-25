package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "driver_vehicle_assignment")
@Getter
@Setter
public class DriverVehicleAssignment {
    /*Driver assignment to school buses is tracked
    Temporary tour assignments remain unaffected

    Driver changes on a bus
    Historical tracking (who drove which bus when)
    Avoids hard-coding driver into vehicle

    📌 endDate = NULL → current assignment
    📌 Only one active assignment per vehicle (enforced later)
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
