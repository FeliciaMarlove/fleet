package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.models.enums.Brand;
import com.soprasteria.fleet.models.enums.FuelType;

import java.time.LocalDate;

public final class CarDTO implements DTOEntity {
    private String plateNumber;
    private Integer kilometers;
    private Brand brand;
    private String model;
    private FuelType fuelType;
    private Double averageConsumption;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean ongoing;
    private String freeText;
    private Integer leasingCompanyId;
    private Integer staffMemberId;
    private Integer carInspectionId;

    public CarDTO(String plateNumber, Brand brand, String model, FuelType fuelType, Double averageConsumption, LocalDate startDate, LocalDate endDate, String freeText, Integer leasingCompanyId, Integer staffMemberId, Integer carInspectionId) {
        this();
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.averageConsumption = averageConsumption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.freeText = freeText;
        this.leasingCompanyId = leasingCompanyId;
        this.staffMemberId = staffMemberId;
        this.carInspectionId = carInspectionId;
    }

    public CarDTO() {
        this.kilometers = 0;
        this.ongoing = true;
    }

    public Integer getCarInspectionId() {
        return carInspectionId;
    }

    public void setCarInspectionId(Integer carInspectionId) {
        this.carInspectionId = carInspectionId;
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
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
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
        this.averageConsumption = Math.abs(averageConsumption);
    }

    public Integer getLeasingCompanyId() {
        return leasingCompanyId;
    }

    public void setLeasingCompanyId(Integer leasingCompanyId) {
        this.leasingCompanyId = leasingCompanyId;
    }

    @Override
    public String toString() {
        return "CarDTO : " + this.getPlateNumber();
    }
}
