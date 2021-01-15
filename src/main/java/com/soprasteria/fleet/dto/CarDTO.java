package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;

import java.time.LocalDate;

public class CarDTO implements DTOEntity {
    private String plateNumber;
    private Integer chassisNumber;
    private LocalDate registrationDate;
    private Integer kilometers;
    private Brand brand;
    private String model;
    private FuelType fuelType;
    private Double averageConsumption;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOngoing;
    private String freeText;
    private Integer leasingCompanyId;
    private Integer staffMemberId;

    public CarDTO(String plateNumber, Integer chassisNumber, LocalDate registrationDate, Brand brand, String model, FuelType fuelType, Double averageConsumption, LocalDate startDate, LocalDate endDate, Boolean isOngoing, String freeText, Integer leasingCompanyId, Integer staffMemberId) {
        this.plateNumber = plateNumber;
        this.chassisNumber = chassisNumber;
        this.registrationDate = registrationDate;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.averageConsumption = averageConsumption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOngoing = isOngoing;
        this.freeText = freeText;
        this.leasingCompanyId = leasingCompanyId;
        this.staffMemberId = staffMemberId;
    }

    public CarDTO() {
        this.kilometers = 0;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getOngoing() {
        return isOngoing;
    }

    public void setOngoing(Boolean ongoing) {
        isOngoing = ongoing;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public Integer getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(Integer staffMemberId) {
        this.staffMemberId = staffMemberId;
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

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
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
                ", fuelType=" + fuelType +
                ", averageConsumption=" + averageConsumption +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isOngoing=" + isOngoing +
                ", freeText='" + freeText + '\'' +
                ", leasingCompanyId=" + leasingCompanyId +
                ", staffMemberId=" + staffMemberId +
                '}';
    }
}
