package com.niranjan.transport.service.impl;

import com.niranjan.transport.dto.TourTripPendingDTO;
import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.TourTripPayment;
import com.niranjan.transport.repository.TourTripPaymentRepository;
import com.niranjan.transport.service.TourTripService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourTripServiceImpl implements TourTripService {

    private final EntityManager entityManager;
    private final TourTripPaymentRepository paymentRepository;

    public TourTripServiceImpl(
            EntityManager entityManager,
            TourTripPaymentRepository paymentRepository) {
        this.entityManager = entityManager;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public BigDecimal calculatePendingAmount(Long tourTripId) {

        TourTrip trip = entityManager.find(TourTrip.class, tourTripId);
        if (trip == null) {
            throw new RuntimeException("Tour trip not found");
        }

        // Use finalAmount if available, else estimatedAmount
        BigDecimal tripAmount = trip.getFinalAmount() != null
                ? trip.getFinalAmount()
                : trip.getEstimatedAmount();

        if (tripAmount == null) {
            return BigDecimal.ZERO;
        }

        List<TourTripPayment> payments =
                paymentRepository.findByTourTrip(trip);

        BigDecimal totalPaid = payments.stream()
                .map(TourTripPayment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pending = tripAmount.subtract(totalPaid);

        return pending.signum() < 0 ? BigDecimal.ZERO : pending;
    }

    @Override
    public List<TourTripPendingDTO> getAllTripsWithPendingAmount() {

        TypedQuery<TourTrip> query =
                entityManager.createQuery(
                        "SELECT t FROM TourTrip t", TourTrip.class);

        List<TourTrip> trips = query.getResultList();

        return trips.stream()
                .map(trip -> {
                    BigDecimal pending = calculatePendingAmount(trip.getId());

                    if (pending.signum() > 0) {
                        BigDecimal tripAmount = trip.getFinalAmount() != null
                                ? trip.getFinalAmount()
                                : trip.getEstimatedAmount();

                        return new TourTripPendingDTO(
                                trip.getId(),
                                trip.getCustomerName(),
                                trip.getTripStartDate(),
                                trip.getTripEndDate(),
                                tripAmount,
                                pending
                        );
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .toList();
    }
}
