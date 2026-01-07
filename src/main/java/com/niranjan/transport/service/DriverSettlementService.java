package com.niranjan.transport.service;

import com.niranjan.transport.dto.DriverSettlementRequestDTO;
import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import com.niranjan.transport.repository.DriverPaymentRepository;
import com.niranjan.transport.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DriverSettlementService {

    private final DriverRepository driverRepository;
    private final DriverPaymentRepository paymentRepository;

    public DriverSettlementService(
            DriverRepository driverRepository,
            DriverPaymentRepository paymentRepository) {
        this.driverRepository = driverRepository;
        this.paymentRepository = paymentRepository;
    }

    public void settleDriverAccount(DriverSettlementRequestDTO request) {

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        BigDecimal amount = request.getSettlementAmount();

        DriverPayment payment = new DriverPayment();
        payment.setDriver(driver);
        payment.setPaymentDate(request.getSettlementDate());
        payment.setRemarks(request.getRemarks());

        /*
         * Settlement is ALWAYS recorded as ADJUSTMENT
         * Sign of amount decides direction
         */
        payment.setPaymentType(DriverPaymentType.ADJUSTMENT);
        payment.setAmount(amount);

        paymentRepository.save(payment);
    }
}
