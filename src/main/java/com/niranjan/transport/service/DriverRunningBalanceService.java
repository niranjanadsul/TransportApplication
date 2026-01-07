package com.niranjan.transport.service;

import com.niranjan.transport.dto.DriverRunningBalanceReport;
import com.niranjan.transport.dto.DriverRunningBalanceRowDTO;
import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import com.niranjan.transport.repository.DriverPaymentRepository;
import com.niranjan.transport.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverRunningBalanceService {

    private final DriverRepository driverRepository;
    private final DriverPaymentRepository paymentRepository;

    public DriverRunningBalanceService(
            DriverRepository driverRepository,
            DriverPaymentRepository paymentRepository) {
        this.driverRepository = driverRepository;
        this.paymentRepository = paymentRepository;
    }

    public DriverRunningBalanceReport getRunningBalanceReport(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<Driver> drivers = driverRepository.findAll();
        List<DriverRunningBalanceRowDTO> rows = new ArrayList<>();

        for (Driver driver : drivers) {

            BigDecimal runningBalance = BigDecimal.ZERO;
            YearMonth current = startMonth;

            while (!current.isAfter(endMonth)) {

                LocalDate from = current.atDay(1);
                LocalDate to = current.atEndOfMonth();

                List<DriverPayment> payments =
                        paymentRepository.findByDriverAndPaymentDateBetween(
                                driver, from, to
                        );

                BigDecimal monthlyNet = calculateMonthlyNet(payments);

                DriverRunningBalanceRowDTO row =
                        new DriverRunningBalanceRowDTO();

                row.setDriverId(driver.getId());
                row.setDriverName(driver.getName());
                row.setMonth(current);
                row.setOpeningBalance(runningBalance);
                row.setMonthlyNet(monthlyNet);

                runningBalance = runningBalance.add(monthlyNet);
                row.setClosingBalance(runningBalance);

                rows.add(row);
                current = current.plusMonths(1);
            }
        }

        DriverRunningBalanceReport report =
                new DriverRunningBalanceReport();
        report.setRows(rows);

        return report;
    }

    // ---------------- HELPERS ----------------

    private BigDecimal calculateMonthlyNet(
            List<DriverPayment> payments) {

        BigDecimal credit = BigDecimal.ZERO;
        BigDecimal debit = BigDecimal.ZERO;

        for (DriverPayment payment : payments) {
            if (payment.getPaymentType() == DriverPaymentType.ADVANCE) {
                debit = debit.add(payment.getAmount());
            } else {
                credit = credit.add(payment.getAmount());
            }
        }
        return credit.subtract(debit);
    }
}
