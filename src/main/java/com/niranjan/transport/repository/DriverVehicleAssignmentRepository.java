package com.niranjan.transport.repository;

import com.niranjan.transport.entity.Driver;
import com.niranjan.transport.entity.DriverVehicleAssignment;
import com.niranjan.transport.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface DriverVehicleAssignmentRepository
        extends JpaRepository<DriverVehicleAssignment, Long> {

    Optional<DriverVehicleAssignment> findByVehicleAndEndDateIsNull(Vehicle vehicle);

    Optional<DriverVehicleAssignment> findByDriverAndEndDateIsNull(Driver driver);

    List<DriverVehicleAssignment> findByVehicle(Vehicle vehicle);
}
