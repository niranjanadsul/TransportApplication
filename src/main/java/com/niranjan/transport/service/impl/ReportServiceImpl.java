package com.niranjan.transport.service.impl;

import com.niranjan.transport.dto.MonthlyProfitLossDto;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import com.niranjan.transport.entity.Expense;
import com.niranjan.transport.entity.StudentFeePayment;
import com.niranjan.transport.entity.TourTripPayment;
import com.niranjan.transport.repository.DriverPaymentRepository;
import com.niranjan.transport.repository.ExpenseRepository;
import com.niranjan.transport.repository.StudentFeePaymentRepository;
import com.niranjan.transport.repository.TourTripPaymentRepository;
import com.niranjan.transport.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final StudentFeePaymentRepository studentFeePaymentRepository;
    private final TourTripPaymentRepository tourTripPaymentRepository;
    private final DriverPaymentRepository driverPaymentRepository;
    private final ExpenseRepository expenseRepository;

    public ReportServiceImpl(
            StudentFeePaymentRepository studentFeePaymentRepository,
            TourTripPaymentRepository tourTripPaymentRepository,
            DriverPaymentRepository driverPaymentRepository,
            ExpenseRepository expenseRepository) {

        this.studentFeePaymentRepository = studentFeePaymentRepository;
        this.tourTripPaymentRepository = tourTripPaymentRepository;
        this.driverPaymentRepository = driverPaymentRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public MonthlyProfitLossDto getMonthlyProfitLoss(int year, int month) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // 1️⃣ Student income
        YearMonth ym = YearMonth.of(year, month);
        LocalDate feeMonth = ym.atDay(1);

        BigDecimal studentIncome = studentFeePaymentRepository
                .findAll()
                .stream()
                .filter(p -> feeMonth.equals(p.getFeeMonth()))
                .map(StudentFeePayment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2️⃣ Tour income
        BigDecimal tourIncome = tourTripPaymentRepository
                .findAll()
                .stream()
                .filter(p -> !p.getPaymentDate().isBefore(start)
                        && !p.getPaymentDate().isAfter(end))
                .map(TourTripPayment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3️⃣ Driver expenses (exclude ADVANCE)
        BigDecimal driverExpense = driverPaymentRepository
                .findByPaymentDateBetween(start, end)
                .stream()
                .filter(p -> p.getPaymentType() != DriverPaymentType.ADVANCE)
                .map(DriverPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4️⃣ Vehicle expenses
        BigDecimal vehicleExpense = expenseRepository
                .findByExpenseDateBetween(start, end)
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MonthlyProfitLossDto(
                year,
                month,
                studentIncome,
                tourIncome,
                driverExpense,
                vehicleExpense
        );
    }
}
