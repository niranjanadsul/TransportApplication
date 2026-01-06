package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentBusAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentBusAssignmentRepository
        extends JpaRepository<StudentBusAssignment, Long> {

    Optional<StudentBusAssignment> findByStudentAndEndDateIsNull(Student student);
    List<StudentBusAssignment> findByStudentAndStartDateLessThanEqualAndEndDateIsNullOrEndDateGreaterThanEqual(
            Student student,
            LocalDate date1,
            LocalDate date2
    );
    Optional<StudentBusAssignment> findFirstByStudentOrderByStartDateDesc(Student student);
}
