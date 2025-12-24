package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tour_trip_payment")
@Getter
@Setter
public class TourTripPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tour_trip_id", nullable = false)
    private TourTrip tourTrip;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(length = 100)
    private String remarks;
}
/*This step depends on the corrected STEP 15 design where:
Trip has estimatedAmount
Trip has finalAmount (known only after completion)

Advance payments are allowed
Multiple payments are allowed
Over-payment / under-payment is possible
Final settlement happens after trip ends

Real-world cases handled:
Customer pays advance based on estimate
Trip extends → final amount increases
Fuel cost changes
Balance collected or refunded later
👉 Therefore:
Payments must NOT depend on finalAmount
They are just raw money receipts

1️⃣ tour_trip
tripStartDate
tripEndDate → filled when trip ends
estimatedAmount
finalAmount → actual bill after trip
This tells us:
Trip is completed AND total amount due is X

2️⃣ tour_trip_payment
Multiple rows per trip
Advance / partial / final payments
No assumptions about completeness
This tells us:
Customer has paid Y so far

*/