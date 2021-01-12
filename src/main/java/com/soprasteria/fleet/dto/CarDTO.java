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
        this();
        this.plateNumber = plateNumber;
        this.chassisNumber = chassisNumber;
        this.registrationDate = registrationDate;
        this.kilometers = kilometers;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.averageConsumption = averageConsumption;
        this.leasingCompanyId = leasingCompanyId;
    }

    public CarDTO() {
        this.isArchived = false;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(Integer chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public Integer getLeasingCompanyId() {
        return leasingCompanyId;
    }

    public void setLeasingCompanyId(Integer leasingCompanyId) {
        this.leasingCompanyId = leasingCompanyId;
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
