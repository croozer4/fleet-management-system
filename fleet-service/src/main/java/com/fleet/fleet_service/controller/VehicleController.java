package com.fleet.fleet_service.controller;

import com.fleet.fleet_service.VehicleService;
import com.fleet.fleet_service.model.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type
    ) {
        return service.getAll(status, type);
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        Vehicle created = service.create(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Vehicle update(@PathVariable UUID id, @RequestBody Vehicle vehicle) {
        return service.update(id, vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/status")
    public Vehicle updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @PostMapping("/{id}/location")
    public Vehicle updateLocation(@PathVariable UUID id,
                                  @RequestParam Double lat,
                                  @RequestParam Double lon) {
        return service.updateLocation(id, lat, lon);
    }
}
