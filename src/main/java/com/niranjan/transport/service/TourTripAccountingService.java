package com.niranjan.transport.service;

import com.niranjan.transport.dto.TourTripAccountingDTO;
import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.TourTripPayment;
import com.niranjan.transport.repository.TourTripPaymentRepository;
import com.niranjan.transport.repository.TourTripRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourTripAccountingService {

    private final TourTripRepository tripRepository;
    private final TourTripPaymentRepository paymentRepository;

    public TourTripAccountingService(
            TourTripRepository tripRepository,
            TourTripPaymentRepository paymentRepository) {

        this.tripRepository = tripRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<TourTripAccountingDTO> getTripAccountingReport() {

        List<TourTripAccountingDTO> result = new ArrayList<>();

        for (TourTrip trip : tripRepository.findAll()) {

            BigDecimal tripAmount =
                    trip.getFinalAmount() != null
                            ? trip.getFinalAmount()
                            : trip.getEstimatedAmount();

            BigDecimal paid = BigDecimal.ZERO;
            List<TourTripPayment> payments =
                    paymentRepository.findByTourTrip(trip);

            for (TourTripPayment payment : payments) {
                paid = paid.add(payment.getAmountPaid());
            }

            BigDecimal pending = tripAmount.subtract(paid);

            TourTripAccountingDTO dto = new TourTripAccountingDTO();
            dto.setTripId(trip.getId());
            dto.setCustomerName(trip.getCustomerName());
            dto.setTripStartDate(trip.getTripStartDate());
            dto.setTripEndDate(trip.getTripEndDate());
            dto.setTripAmount(tripAmount);
            dto.setTotalPaid(paid);
            dto.setPendingAmount(pending);

            result.add(dto);
        }

        return result;
    }
}
