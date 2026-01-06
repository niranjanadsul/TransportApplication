package com.niranjan.transport.service;

import com.niranjan.transport.dto.VehicleMonthlyFinanceReport;
import com.niranjan.transport.dto.VehicleMonthlyFinanceRow;
import com.niranjan.transport.entity.*;
import com.niranjan.transport.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Service
public class VehicleReportService {

    private final VehicleRepository vehicleRepository;
    private final StudentFeePaymentRepository studentPaymentRepository;
    private final StudentBusAssignmentRepository studentBusAssignmentRepository;
    private final TourTripPaymentRepository tourPaymentRepository;
    private final ExpenseRepository expenseRepository;

    public VehicleReportService(
            VehicleRepository vehicleRepository,
            StudentFeePaymentRepository studentPaymentRepository,
            StudentBusAssignmentRepository studentBusAssignmentRepository,
            TourTripPaymentRepository tourPaymentRepository,
            ExpenseRepository expenseRepository) {

        this.vehicleRepository = vehicleRepository;
        this.studentPaymentRepository = studentPaymentRepository;
        this.studentBusAssignmentRepository = studentBusAssignmentRepository;
        this.tourPaymentRepository = tourPaymentRepository;
        this.expenseRepository = expenseRepository;
    }

    public VehicleMonthlyFinanceReport getVehicleMonthlyFinance(
            YearMonth startMonth,
            YearMonth endMonth) {

        List<YearMonth> months = generateMonths(startMonth, endMonth);
        List<Vehicle> vehicles = vehicleRepository.findAll();

        List<VehicleMonthlyFinanceRow> rows = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            VehicleMonthlyFinanceRow row = new VehicleMonthlyFinanceRow();
            row.setVehicleId(vehicle.getId());
            row.setVehicleNumber(vehicle.getVehicleNumber());

            Map<YearMonth, BigDecimal> schoolIncome = initMap(months);
            Map<YearMonth, BigDecimal> tourIncome = initMap(months);
            Map<YearMonth, BigDecimal> expenses = initMap(months);

            // SCHOOL INCOME (student payments)
            for (StudentFeePayment p : studentPaymentRepository.findAll()) {

                YearMonth ym = YearMonth.from(p.getFeeMonth());
                if (!schoolIncome.containsKey(ym)) {
                    continue;
                }

                // Find vehicle for student during that month
                List<StudentBusAssignment> assignments =
                        studentBusAssignmentRepository
                                .findByStudentAndStartDateLessThanEqualAndEndDateIsNullOrEndDateGreaterThanEqual(
                                        p.getStudent(),
                                        p.getFeeMonth(),
                                        p.getFeeMonth()
                                );

                for (StudentBusAssignment a : assignments) {
                    if (a.getVehicle().getId().equals(vehicle.getId())) {
                        schoolIncome.put(
                                ym,
                                schoolIncome.get(ym).add(p.getAmountPaid())
                        );
                    }
                }
            }


            // TOUR INCOME
            for (TourTripPayment p : tourPaymentRepository.findAll()) {
                if (p.getTourTrip().getVehicle().getId()
                        .equals(vehicle.getId())) {

                    YearMonth ym = YearMonth.from(p.getPaymentDate());
                    if (tourIncome.containsKey(ym)) {
                        tourIncome.put(
                                ym,
                                tourIncome.get(ym).add(p.getAmountPaid())
                        );
                    }
                }
            }

            // EXPENSES
            for (Expense e : expenseRepository.findAll()) {
                if (e.getVehicle() != null &&
                        e.getVehicle().getId().equals(vehicle.getId())) {

                    YearMonth ym = YearMonth.from(e.getExpenseDate());
                    if (expenses.containsKey(ym)) {
                        expenses.put(
                                ym,
                                expenses.get(ym).add(e.getAmount())
                        );
                    }
                }
            }

            row.setSchoolIncome(schoolIncome);
            row.setTourIncome(tourIncome);
            row.setExpenses(expenses);

            rows.add(row);
        }

        VehicleMonthlyFinanceReport report = new VehicleMonthlyFinanceReport();
        report.setMonths(months);
        report.setRows(rows);

        return report;
    }

    // ---------- helpers ----------

    private List<YearMonth> generateMonths(YearMonth start, YearMonth end) {
        List<YearMonth> list = new ArrayList<>();
        YearMonth current = start;
        while (!current.isAfter(end)) {
            list.add(current);
            current = current.plusMonths(1);
        }
        return list;
    }

    private Map<YearMonth, BigDecimal> initMap(List<YearMonth> months) {
        Map<YearMonth, BigDecimal> map = new LinkedHashMap<>();
        for (YearMonth m : months) {
            map.put(m, BigDecimal.ZERO);
        }
        return map;
    }
}
