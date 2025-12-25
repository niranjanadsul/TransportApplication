package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Expense;
import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByVehicle(Vehicle vehicle);

    List<Expense> findByTourTrip(TourTrip tourTrip);

    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
}
