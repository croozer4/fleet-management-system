package com.fleet.auth_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private String type;

    @Column
    private Integer capacityKg;

    @Column(nullable = false)
    private String status;

    private LocalDate lastMaintenance;

    private Long mileage;
    private Double lat;
    private Double lon;

    public Vehicle() {

    }

    public Vehicle(String vin,
                   String plate,
                   String type,
                   Integer capacityKg,
                   String status,
                   LocalDate lastMaintenance,
                   Long mileage,
                   Double lat,
                   Double lon) {
        this.vin = vin;
        this.plate = plate;
        this.type = type;
        this.capacityKg = capacityKg;
        this.status = status;
        this.lastMaintenance = lastMaintenance;
        this.mileage = mileage;
        this.lat = lat;
        this.lon = lon;
    }

    public UUID getId() {
        return this.id;
    }

    public String getPlate(){
        return this.plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacityKg() {
        return this.capacityKg;
    }

    public void setCapacityKg(Integer capacityKg) {
        this.capacityKg = capacityKg;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLastMaintenance() {
        return this.lastMaintenance;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public Long getMileage() {
        return this.mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
