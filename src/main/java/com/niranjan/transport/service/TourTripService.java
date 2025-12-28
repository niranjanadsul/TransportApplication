package com.niranjan.transport.service;

import com.niranjan.transport.dto.TourTripPendingDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TourTripService {
    List<TourTripPendingDTO> getAllTripsWithPendingAmount();

    BigDecimal calculatePendingAmount(Long tourTripId);
}

