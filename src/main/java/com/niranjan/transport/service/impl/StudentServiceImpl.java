package com.niranjan.transport.service.impl;

import com.niranjan.transport.dto.StudentPendingFeeDTO;
import com.niranjan.transport.entity.*;
import com.niranjan.transport.repository.*;
import com.niranjan.transport.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final VehicleRepository vehicleRepository;
    private final StudentBusAssignmentRepository assignmentRepository;
    private final StudentFeePlanRepository studentFeePlanRepository;
    private final StudentFeePaymentRepository studentFeePaymentRepository;

    public StudentServiceImpl(
            StudentRepository studentRepository,
            VehicleRepository vehicleRepository,
            StudentBusAssignmentRepository assignmentRepository,
            StudentFeePlanRepository studentFeePlanRepository,
            StudentFeePaymentRepository studentFeePaymentRepository) {
        this.studentRepository = studentRepository;
        this.vehicleRepository = vehicleRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentFeePlanRepository=studentFeePlanRepository;
        this.studentFeePaymentRepository=studentFeePaymentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void assignStudentToVehicle(
            Long studentId,
            Long vehicleId,
            LocalDate startDate) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        assignmentRepository
                .findByStudentAndEndDateIsNull(student)
                .ifPresent(existing -> {
                    existing.setEndDate(startDate.minusDays(1));
                    assignmentRepository.save(existing);
                });

        StudentBusAssignment newAssignment = new StudentBusAssignment();
        newAssignment.setStudent(student);
        newAssignment.setVehicle(vehicle);
        newAssignment.setStartDate(startDate);

        assignmentRepository.save(newAssignment);
    }

    @Override
    public void changeStudentFee(Long studentId, BigDecimal newMonthlyFee, LocalDate effectiveFrom) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentFeePlanRepository
                .findByStudentAndEndDateIsNull(student)
                .ifPresent(existing -> {
                    existing.setEndDate(effectiveFrom.minusDays(1));
                    studentFeePlanRepository.save(existing);
                });

        StudentFeePlan newPlan = new StudentFeePlan();
        newPlan.setStudent(student);
        newPlan.setMonthlyFee(newMonthlyFee);
        newPlan.setStartDate(effectiveFrom);

        studentFeePlanRepository.save(newPlan);
    }

    @Override
    public void deactivateStudent(Long studentId, LocalDate endDate) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        assignmentRepository
                .findByStudentAndEndDateIsNull(student)
                .ifPresent(assignment -> {
                    assignment.setEndDate(endDate);
                    assignmentRepository.save(assignment);
                });

        studentFeePlanRepository
                .findByStudentAndEndDateIsNull(student)
                .ifPresent(plan -> {
                    plan.setEndDate(endDate);
                    studentFeePlanRepository.save(plan);
                });
    }

    @Override
    public BigDecimal calculatePendingFee(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        BigDecimal pending = BigDecimal.ZERO;

        List<StudentFeePlan> feePlans =
                studentFeePlanRepository.findByStudent(student);

        for (StudentFeePlan plan : feePlans) {

            LocalDate start = plan.getStartDate().withDayOfMonth(1);
            LocalDate end = (plan.getEndDate() != null
                    ? plan.getEndDate()
                    : LocalDate.now()).withDayOfMonth(1);

            LocalDate month = start;

            while (!month.isAfter(end)) {

                BigDecimal monthlyFee = plan.getMonthlyFee();

                BigDecimal paidForMonth = studentFeePaymentRepository
                        .findByStudentAndFeeMonth(student, month)
                        .stream()
                        .map(StudentFeePayment::getAmountPaid)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal monthPending =
                        monthlyFee.subtract(paidForMonth);

                if (monthPending.signum() > 0) {
                    pending = pending.add(monthPending);
                }

                month = month.plusMonths(1);
            }
        }

        return pending;
    }

    @Override
    public List<StudentPendingFeeDTO> getPendingFeeSummary() {

        List<Student> students = studentRepository.findAll();
        List<StudentPendingFeeDTO> result = new ArrayList<>();

        for (Student student : students) {

            BigDecimal pending = calculatePendingFee(student.getId());

            if (pending.signum() <= 0) {
                continue; // skip fully paid students
            }

            StudentPendingFeeDTO dto = new StudentPendingFeeDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            dto.setPendingAmount(pending);

            assignmentRepository
                    .findByStudentAndEndDateIsNull(student)
                    .ifPresent(assignment ->
                            dto.setVehicleNumber(
                                    assignment.getVehicle().getVehicleNumber()
                            )
                    );

            result.add(dto);
        }

        return result;
    }


}
