package com.niranjan.transport.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TourTripPaymentService {

    void recordPayment(
            Long tourTripId,
            BigDecimal amount,
            LocalDate paymentDate,
            String remarks
    );
}
