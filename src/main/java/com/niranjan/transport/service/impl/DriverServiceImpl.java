package com.niranjan.transport.service.impl;

import com.niranjan.transport.dto.DriverMonthlySummaryDTO;
import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import com.niranjan.transport.repository.DriverPaymentRepository;
import com.niranjan.transport.service.DriverService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DriverServiceImpl implements DriverService {

    private final DriverPaymentRepository paymentRepository;
    private final EntityManager entityManager;

    public DriverServiceImpl(
            DriverPaymentRepository paymentRepository,
            EntityManager entityManager) {
        this.paymentRepository = paymentRepository;
        this.entityManager = entityManager;
    }

    @Override
    public DriverMonthlySummaryDTO getMonthlyPaymentSummary(
            Long driverId,
            int year,
            int month) {

        Driver driver = entityManager.find(Driver.class, driverId);
        if (driver == null) {
            throw new RuntimeException("Driver not found");
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<DriverPayment> payments =
                paymentRepository.findByDriverAndPaymentDateBetween(
                        driver, start, end);

        BigDecimal salary = BigDecimal.ZERO;
        BigDecimal tourDaily = BigDecimal.ZERO;
        BigDecimal advance = BigDecimal.ZERO;
        BigDecimal adjustment = BigDecimal.ZERO;

        for (DriverPayment payment : payments) {

            if (payment.getPaymentType() == DriverPaymentType.SALARY) {
                salary = salary.add(payment.getAmount());

            } else if (payment.getPaymentType() == DriverPaymentType.TOUR_DAILY) {
                tourDaily = tourDaily.add(payment.getAmount());

            } else if (payment.getPaymentType() == DriverPaymentType.ADVANCE) {
                advance = advance.add(payment.getAmount());

            } else if (payment.getPaymentType() == DriverPaymentType.ADJUSTMENT) {
                adjustment = adjustment.add(payment.getAmount());
            }
        }

        return new DriverMonthlySummaryDTO(
                driverId,
                year,
                month,
                salary,
                tourDaily.add(adjustment),
                advance
        );
    }
}
