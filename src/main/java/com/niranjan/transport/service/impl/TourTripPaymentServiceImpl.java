package com.niranjan.transport.service.impl;

import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.TourTripPayment;
import com.niranjan.transport.repository.TourTripPaymentRepository;
import com.niranjan.transport.service.TourTripPaymentService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class TourTripPaymentServiceImpl
        implements TourTripPaymentService {

    private final TourTripPaymentRepository paymentRepository;
    private final EntityManager entityManager;

    public TourTripPaymentServiceImpl(
            TourTripPaymentRepository paymentRepository,
            EntityManager entityManager) {
        this.paymentRepository = paymentRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void recordPayment(
            Long tourTripId,
            BigDecimal amount,
            LocalDate paymentDate,
            String remarks) {

        TourTrip trip = entityManager.getReference(TourTrip.class, tourTripId);

        TourTripPayment payment = new TourTripPayment();
        payment.setTourTrip(trip);
        payment.setAmountPaid(amount);
        payment.setPaymentDate(paymentDate);
        payment.setRemarks(remarks);

        paymentRepository.save(payment);
    }
}
