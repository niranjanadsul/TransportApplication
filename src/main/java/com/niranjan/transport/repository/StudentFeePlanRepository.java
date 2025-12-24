package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentFeePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentFeePlanRepository
        extends JpaRepository<StudentFeePlan, Long> {

    Optional<StudentFeePlan> findByStudentAndEndDateIsNull(Student student);
}
