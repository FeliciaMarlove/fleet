package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.enums.DiscrepancyLevel;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.enums.FuelType;

import java.time.LocalDateTime;

public class TankFillingDTO implements DTOEntity {
    private Integer tankFillingId;
    private Integer kmBefore;
    private Integer kmAfter;
    private boolean discrepancy;
    private DiscrepancyLevel discrepancyLevel;
    private DiscrepancyType discrepancyType;
    private LocalDateTime dateTimeFilling;
    private FuelType fuelType;
    private Double liters;
    private String plateNumber;

    public TankFillingDTO(Integer tankFillingId, Integer kmBefore, Integer kmAfter, boolean discrepancy, DiscrepancyLevel discrepancyLevel, DiscrepancyType discrepancyType, LocalDateTime dateTimeFilling, FuelType fuelType, Double liters, String plateNumber) {
        this.tankFillingId = tankFillingId;
        this.kmBefore = kmBefore;
        this.kmAfter = kmAfter;
        this.discrepancy = discrepancy;
        this.discrepancyLevel = discrepancyLevel;
        this.discrepancyType = discrepancyType;
        this.dateTimeFilling = dateTimeFilling;
        this.fuelType = fuelType;
        this.liters = liters;
        this.plateNumber = plateNumber;
    }

    public Integer getTankFillingId() {
        return tankFillingId;
    }

    public void setTankFillingId(Integer tankFillingId) {
        this.tankFillingId = tankFillingId;
    }

    public Integer getKmBefore() {
        return kmBefore;
    }

    public void setKmBefore(Integer kmBefore) {
        this.kmBefore = kmBefore;
    }

    public Integer getKmAfter() {
        return kmAfter;
    }

    public void setKmAfter(Integer kmAfter) {
        this.kmAfter = kmAfter;
    }

    public boolean isDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(boolean discrepancy) {
        this.discrepancy = discrepancy;
    }

    public DiscrepancyLevel getDiscrepancyLevel() {
        return discrepancyLevel;
    }

    public void setDiscrepancyLevel(DiscrepancyLevel discrepancyLevel) {
        this.discrepancyLevel = discrepancyLevel;
    }

    public DiscrepancyType getDiscrepancyType() {
        return discrepancyType;
    }

    public void setDiscrepancyType(DiscrepancyType discrepancyType) {
        this.discrepancyType = discrepancyType;
    }

    public LocalDateTime getDateTimeFilling() {
        return dateTimeFilling;
    }

    public void setDateTimeFilling(LocalDateTime dateTimeFilling) {
        this.dateTimeFilling = dateTimeFilling;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public TankFillingDTO() {
    }

    @Override
    public String toString() {
        return "TankFillingDTO{" +
                "tankFillingId=" + tankFillingId +
                ", kmBefore=" + kmBefore +
                ", kmAfter=" + kmAfter +
                ", discrepancy=" + discrepancy +
                ", discrepancyLevel=" + discrepancyLevel +
                ", discrepancyType=" + discrepancyType +
                ", dateTimeFilling=" + dateTimeFilling +
                ", fuelType=" + fuelType +
                ", liters=" + liters +
                ", plateNumber='" + plateNumber + '\'' +
                '}';
    }
}
