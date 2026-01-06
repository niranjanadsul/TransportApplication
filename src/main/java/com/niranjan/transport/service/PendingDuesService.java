package com.niranjan.transport.service;

import com.niranjan.transport.dto.StudentPendingFeeDTO;
import com.niranjan.transport.dto.TourTripPendingDTO;
import com.niranjan.transport.entity.*;
import com.niranjan.transport.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class PendingDuesService {

    private final StudentRepository studentRepository;
    private final StudentFeePlanRepository feePlanRepository;
    private final StudentFeePaymentRepository paymentRepository;
    private final StudentBusAssignmentRepository busAssignmentRepository;

    private final TourTripRepository tourTripRepository;
    private final TourTripPaymentRepository tourTripPaymentRepository;

    public PendingDuesService(
            StudentRepository studentRepository,
            StudentFeePlanRepository feePlanRepository,
            StudentFeePaymentRepository paymentRepository,
            StudentBusAssignmentRepository busAssignmentRepository,
            TourTripRepository tourTripRepository,
            TourTripPaymentRepository tourTripPaymentRepository) {

        this.studentRepository = studentRepository;
        this.feePlanRepository = feePlanRepository;
        this.paymentRepository = paymentRepository;
        this.busAssignmentRepository = busAssignmentRepository;
        this.tourTripRepository = tourTripRepository;
        this.tourTripPaymentRepository = tourTripPaymentRepository;
    }

    // ================= STUDENT PENDING =================

    public List<StudentPendingFeeDTO> getStudentPendingFees() {

        List<StudentPendingFeeDTO> result = new ArrayList<>();

        for (Student student : studentRepository.findAll()) {

            BigDecimal expected = BigDecimal.ZERO;
            BigDecimal paid = BigDecimal.ZERO;

            // ---- Expected fee calculation ----
            List<StudentFeePlan> plans = feePlanRepository.findByStudent(student);

            for (StudentFeePlan plan : plans) {

                YearMonth start = YearMonth.from(plan.getStartDate());
                YearMonth end = plan.getEndDate() == null
                        ? YearMonth.now()
                        : YearMonth.from(plan.getEndDate());

                BigDecimal monthlyFee = plan.getMonthlyFee();

                YearMonth cursor = start;
                while (!cursor.isAfter(end)) {
                    expected = expected.add(monthlyFee);
                    cursor = cursor.plusMonths(1);
                }
            }

            // ---- Paid amount calculation ----
            List<StudentFeePayment> payments =
                    paymentRepository.findByStudent(student);

            for (StudentFeePayment payment : payments) {
                paid = paid.add(payment.getAmountPaid());
            }

            BigDecimal pending = expected.subtract(paid);

            if (pending.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            StudentPendingFeeDTO dto = new StudentPendingFeeDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            dto.setPendingAmount(pending);

            busAssignmentRepository
                    .findFirstByStudentOrderByStartDateDesc(student)
                    .ifPresent(assign ->
                            dto.setVehicleNumber(
                                    assign.getVehicle().getVehicleNumber()
                            )
                    );

            result.add(dto);
        }

        return result;
    }


    // ================= TOUR PENDING =================

    public List<TourTripPendingDTO> getTourTripPendingPayments() {

        List<TourTripPendingDTO> result = new ArrayList<>();

        for (TourTrip trip : tourTripRepository.findAll()) {

            BigDecimal tripAmount =
                    trip.getFinalAmount() != null
                            ? trip.getFinalAmount()
                            : trip.getEstimatedAmount();

            if (tripAmount == null) continue;

            BigDecimal paid = tourTripPaymentRepository
                    .findByTourTrip(trip)
                    .stream()
                    .map(TourTripPayment::getAmountPaid)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal pending = tripAmount.subtract(paid);

            if (pending.compareTo(BigDecimal.ZERO) <= 0) continue;

            result.add(
                    new TourTripPendingDTO(
                            trip.getId(),
                            trip.getCustomerName(),
                            trip.getTripStartDate(),
                            trip.getTripEndDate(),
                            tripAmount,
                            pending
                    )
            );
        }

        return result;
    }
}
