package com.niranjan.transport.service;

import com.niranjan.transport.dto.DriverMonthlyAccountReport;
import com.niranjan.transport.dto.DriverMonthlyAccountRowDTO;
import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import com.niranjan.transport.repository.DriverPaymentRepository;
import com.niranjan.transport.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class DriverAccountingReportService {

    private final DriverRepository driverRepository;
    private final DriverPaymentRepository paymentRepository;

    public DriverAccountingReportService(
            DriverRepository driverRepository,
            DriverPaymentRepository paymentRepository) {
        this.driverRepository = driverRepository;
        this.paymentRepository = paymentRepository;
    }

    public DriverMonthlyAccountReport getDriverMonthlyAccountReport(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<YearMonth> months = generateMonths(startMonth, endMonth);
        List<Driver> drivers = driverRepository.findAll();

        List<DriverMonthlyAccountRowDTO> rows = new ArrayList<>();

        for (Driver driver : drivers) {

            for (YearMonth month : months) {

                LocalDate start = month.atDay(1);
                LocalDate end = month.atEndOfMonth();

                List<DriverPayment> payments =
                        paymentRepository.findByDriverAndPaymentDateBetween(
                                driver, start, end
                        );

                BigDecimal salary = sumByType(payments, DriverPaymentType.SALARY);
                BigDecimal advance = sumByType(payments, DriverPaymentType.ADVANCE);
                BigDecimal tour = sumByType(payments, DriverPaymentType.TOUR_DAILY);
                BigDecimal adjustment = sumByType(payments, DriverPaymentType.ADJUSTMENT);

                BigDecimal net =
                        salary
                                .add(tour)
                                .add(adjustment)
                                .subtract(advance);

                DriverMonthlyAccountRowDTO row = new DriverMonthlyAccountRowDTO();
                row.setDriverId(driver.getId());
                row.setDriverName(driver.getName());
                row.setMonth(month);
                row.setSalaryPaid(salary);
                row.setAdvanceTaken(advance);
                row.setTourPayments(tour);
                row.setAdjustments(adjustment);
                row.setNetAmount(net);

                rows.add(row);
            }
        }

        DriverMonthlyAccountReport report = new DriverMonthlyAccountReport();
        report.setMonths(months);
        report.setRows(rows);

        return report;
    }

    // ---------------- HELPERS ----------------

    private BigDecimal sumByType(
            List<DriverPayment> payments,
            DriverPaymentType type) {

        return payments.stream()
                .filter(p -> p.getPaymentType() == type)
                .map(DriverPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<YearMonth> generateMonths(
            YearMonth start,
            YearMonth end) {

        List<YearMonth> months = new ArrayList<>();
        YearMonth current = start;

        while (!current.isAfter(end)) {
            months.add(current);
            current = current.plusMonths(1);
        }
        return months;
    }
}
