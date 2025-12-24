package com.niranjan.transport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "tour_trip_id")
    private TourTrip tourTrip;

    @Column(length = 150)
    private String remarks;
}
/*
1️⃣ Tour-trip related expenses

Fuel for a specific trip

Toll charges

Repairs during/after a trip

Lodging, etc.

These are already handled by our current Expense entity via the tourTrip optional field.


2️⃣ Regular vehicle operational expenses (not tied to a trip)

Daily fuel for school buses

Vehicle insurance

Permit renewals

Maintenance

How to fit these in design:

We don’t need a separate entity, but we use the same Expense entity:

Set vehicle → the vehicle the expense belongs to

Set tourTrip → NULL (since it’s not tied to a trip)

Set type → FUEL / REPAIR / OTHER / etc.

Optionally use remarks → "Monthly insurance", "Permit renewal", etc.


Example:
id	type	amount	expense_date	vehicle_id	tour_trip_id	remarks
1	FUEL	2000	2025-12-23	101	NULL	Daily fuel
2	INSURANCE	5000	2025-12-10	101	NULL	Vehicle insurance
3	FUEL	1500	2025-12-20	102	201	Tour fuel
4	TOLL	300	2025-12-21	NULL	201	Trip toll

✅ Single table handles both tour-trip and regular operational expenses
✅ tourTrip is NULL for regular vehicle expenses
✅ Reports can filter by vehicle only, or tourTrip only

Important: Validation Rules (to enforce later)

Either vehicle or tourTrip must be set

If tourTrip is set → vehicle optional

If tourTrip is NULL → vehicle must be set for regular expenses

This keeps the table flexible and covers all real-world vehicle expenses.

*/