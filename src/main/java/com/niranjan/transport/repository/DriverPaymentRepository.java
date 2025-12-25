package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverPayment;
import com.niranjan.transport.entity.DriverPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DriverPaymentRepository
        extends JpaRepository<DriverPayment, Long> {

    List<DriverPayment> findByDriver(Driver driver);

    List<DriverPayment> findByPaymentType(DriverPaymentType paymentType);

    List<DriverPayment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<DriverPayment> findByDriverAndPaymentDateBetween(
            Driver driver,
            LocalDate startDate,
            LocalDate endDate
    );
}
