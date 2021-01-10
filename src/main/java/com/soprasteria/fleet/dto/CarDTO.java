package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;

import java.time.LocalDate;

public class CarDTO implements DTOEntity {
    private String plateNumber;
    private Integer chassisNumber;
    private LocalDate registrationDate;
    private Double kilometers;
    private Brand brand;
    private String model;
    private FuelType fuel;
    private Double averageConsumption;
    private boolean isArchived;
    private Integer leasingCompanyId;

    public CarDTO(String plateNumber, Integer chassisNumber, LocalDate registrationDate, Double kilometers, Brand brand, String model, FuelType fuel, Double averageConsumption, boolean isArchived, Integer leasingCompanyId) {
        this.plateNumber = plateNumber;
        this.chassisNumber = chassisNumber;
        this.registrationDate = registrationDate;
        this.kilometers = kilometers;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.averageConsumption = averageConsumption;
        this.isArchived = isArchived;
        this.leasingCompanyId = leasingCompanyId;
    }

    public CarDTO() {
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "plateNumber='" + plateNumber + '\'' +
                ", chassisNumber=" + chassisNumber +
                ", registrationDate=" + registrationDate +
                ", kilometers=" + kilometers +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", fuel=" + fuel +
                ", averageConsumption=" + averageConsumption +
                ", isArchived=" + isArchived +
                ", leasingCompanyId=" + leasingCompanyId +
                '}';
    }


}
