package com.niranjan.transport.service;

import com.niranjan.transport.dto.TourPaymentMatrixReport;
import com.niranjan.transport.dto.TourPaymentMatrixRow;
import com.niranjan.transport.entity.TourTrip;
import com.niranjan.transport.entity.TourTripPayment;
import com.niranjan.transport.repository.TourTripPaymentRepository;
import com.niranjan.transport.repository.TourTripRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Service
public class TourReportService {

    private final TourTripRepository tourTripRepository;
    private final TourTripPaymentRepository paymentRepository;

    public TourReportService(
            TourTripRepository tourTripRepository,
            TourTripPaymentRepository paymentRepository) {
        this.tourTripRepository = tourTripRepository;
        this.paymentRepository = paymentRepository;
    }

    public TourPaymentMatrixReport getTourPaymentMatrix(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<YearMonth> months = generateMonths(startMonth, endMonth);
        List<TourTrip> trips = tourTripRepository.findAll();

        List<TourPaymentMatrixRow> rows = new ArrayList<>();

        for (TourTrip trip : trips) {

            TourPaymentMatrixRow row = new TourPaymentMatrixRow();
            row.setTourTripId(trip.getId());
            row.setCustomerName(trip.getCustomerName());

            Map<YearMonth, BigDecimal> monthMap = initializeMonthMap(months);

            List<TourTripPayment> payments =
                    paymentRepository.findByTourTrip(trip);

            for (TourTripPayment payment : payments) {

                YearMonth paymentMonth =
                        YearMonth.from(payment.getPaymentDate());

                if (monthMap.containsKey(paymentMonth)) {
                    BigDecimal existing = monthMap.get(paymentMonth);
                    monthMap.put(
                            paymentMonth,
                            existing.add(payment.getAmountPaid())
                    );
                }
            }

            row.setMonthlyPayments(monthMap);
            rows.add(row);
        }

        TourPaymentMatrixReport report = new TourPaymentMatrixReport();
        report.setMonths(months);
        report.setRows(rows);

        return report;
    }

    // ---------------- HELPERS ----------------

    private List<YearMonth> generateMonths(
            YearMonth start,
            YearMonth end) {

        List<YearMonth> months = new ArrayList<>();
        YearMonth current = start;

        while (!current.isAfter(end)) {
            months.add(current);
            current = current.plusMonths(1);
        }
        return months;
    }

    private Map<YearMonth, BigDecimal> initializeMonthMap(
            List<YearMonth> months) {

        Map<YearMonth, BigDecimal> map = new LinkedHashMap<>();
        for (YearMonth month : months) {
            map.put(month, BigDecimal.ZERO);
        }
        return map;
    }
}
