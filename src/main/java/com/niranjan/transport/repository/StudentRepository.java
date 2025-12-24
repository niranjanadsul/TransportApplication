package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Student;
import com.niranjan.transport.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByStatus(StudentStatus status);
}
