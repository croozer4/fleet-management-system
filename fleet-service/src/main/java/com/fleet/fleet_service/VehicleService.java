package com.fleet.fleet_service;

import com.fleet.fleet_service.model.Vehicle;
import com.fleet.fleet_service.repository.VehicleRepository;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> getAll(String status, String type) {
        if (status != null) {
            return repository.findByStatus(status);
        }

        if(type != null){
            repository.findByType(type);
        }

        return repository.findAll();
    }

    public Vehicle getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + id));
    }

    public Vehicle create(Vehicle vehicle) {
        if(vehicle.getStatus() == null){
            vehicle.setStatus("AVAILABLE");
        }
        return repository.save(vehicle);
    }

    public Vehicle update(UUID id, Vehicle updated) {
        Vehicle existing = getById(id);
        existing.setVin(updated.getVin());
        existing.setPlate(updated.getPlate());
        existing.setType(updated.getType());
        existing.setCapacityKg(updated.getCapacityKg());
        existing.setStatus(updated.getStatus());
        existing.setLastMaintenance(updated.getLastMaintenance());
        existing.setMileage(updated.getMileage());
        existing.setLat(updated.getLat());
        existing.setLon(updated.getLon());
        return repository.save(existing);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Vehicle updateStatus(UUID id, String status) {
        Vehicle v = getById(id);
        v.setStatus(status);
        return repository.save(v);
    }

    public Vehicle updateLocation(UUID id, Double lat, Double lon) {
        Vehicle v = getById(id);
        v.setLat(lat);
        v.setLon(lon);
        return repository.save(v);
    }
}
