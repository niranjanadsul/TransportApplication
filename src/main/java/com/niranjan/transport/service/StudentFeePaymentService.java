package com.niranjan.transport.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface StudentFeePaymentService {

    void recordPayment(
            Long studentId,
            BigDecimal amount,
            LocalDate paymentDate,
            String remarks
    );
}
