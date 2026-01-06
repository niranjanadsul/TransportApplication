package com.niranjan.transport.repository;

import com.niranjan.transport.entity.TourTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TourTripRepository
        extends JpaRepository<TourTrip, Long> {

    // Fetch all trips that started within a date range
    List<TourTrip> findByTripStartDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Optional: fetch active (not yet ended) trips
    List<TourTrip> findByTripEndDateIsNull();
}
