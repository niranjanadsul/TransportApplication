package com.niranjan.transport.service.impl;

import com.niranjan.transport.dto.VehicleMonthlyExpenseDTO;
import com.niranjan.transport.entity.Expense;
import com.niranjan.transport.entity.ExpenseType;
import com.niranjan.transport.entity.Vehicle;
import com.niranjan.transport.repository.ExpenseRepository;
import com.niranjan.transport.service.VehicleService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleServiceImpl implements VehicleService {

    private final ExpenseRepository expenseRepository;
    private final EntityManager entityManager;

    public VehicleServiceImpl(
            ExpenseRepository expenseRepository,
            EntityManager entityManager) {
        this.expenseRepository = expenseRepository;
        this.entityManager = entityManager;
    }

    @Override
    public VehicleMonthlyExpenseDTO getMonthlyExpenseSummary(
            Long vehicleId,
            int year,
            int month) {

        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Expense> expenses =
                expenseRepository.findByExpenseDateBetween(start, end)
                        .stream()
                        .filter(e -> vehicle.equals(e.getVehicle()))
                        .toList();

        BigDecimal fuel = BigDecimal.ZERO;
        BigDecimal repair = BigDecimal.ZERO;
        BigDecimal fine = BigDecimal.ZERO;
        BigDecimal toll = BigDecimal.ZERO;
        BigDecimal other = BigDecimal.ZERO;

        for (Expense expense : expenses) {
            switch (expense.getType()) {
                case FUEL -> fuel = fuel.add(expense.getAmount());
                case REPAIR -> repair = repair.add(expense.getAmount());
                case FINE -> fine = fine.add(expense.getAmount());
                case TOLL -> toll = toll.add(expense.getAmount());
                case OTHER -> other = other.add(expense.getAmount());
            }
        }

        return new VehicleMonthlyExpenseDTO(
                vehicleId,
                year,
                month,
                fuel,
                repair,
                fine,
                toll,
                other
        );
    }
}
