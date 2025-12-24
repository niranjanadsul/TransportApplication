package com.niranjan.transport.repository;

import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.TourTripPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourTripPaymentRepository
        extends JpaRepository<TourTripPayment, Long> {

    List<TourTripPayment> findByTourTrip(TourTrip tourTrip);
}
