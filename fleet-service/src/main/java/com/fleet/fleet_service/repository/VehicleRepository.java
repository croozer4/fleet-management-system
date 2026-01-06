package com.fleet.fleet_service.repository;

import com.fleet.fleet_service.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    List<Vehicle> findByStatus(String status);
    List<Vehicle> findByType(String type);
}
