package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentBusAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentBusAssignmentRepository
        extends JpaRepository<StudentBusAssignment, Long> {

    Optional<StudentBusAssignment> findByStudentAndEndDateIsNull(Student student);
}
