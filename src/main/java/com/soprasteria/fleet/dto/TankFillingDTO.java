package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.models.enums.DiscrepancyType;
import com.soprasteria.fleet.models.enums.FuelType;

import java.time.LocalDateTime;

public final class TankFillingDTO implements DTOEntity {
    private Integer tankFillingId;
    private Integer kmBefore;
    private Integer kmAfter;
    private Boolean discrepancy;
    private DiscrepancyType discrepancyType;
    private LocalDateTime dateTimeFilling;
    private FuelType fuelType;
    private Double liters;
    private String plateNumber;
    private Double consumption;
    private Integer correctionForId;
    private Integer correctedById;

    public TankFillingDTO(Integer tankFillingId, Integer kmBefore, Integer kmAfter, Boolean discrepancy, DiscrepancyType discrepancyType, LocalDateTime dateTimeFilling, FuelType fuelType, Double liters, String plateNumber, Double consumption, Integer correctionForId, Integer correctedById) {
        this.tankFillingId = tankFillingId;
        this.kmBefore = kmBefore;
        this.kmAfter = kmAfter;
        this.discrepancy = discrepancy;
        this.discrepancyType = discrepancyType;
        this.dateTimeFilling = dateTimeFilling;
        this.fuelType = fuelType;
        this.liters = liters;
        this.plateNumber = plateNumber;
        this.consumption = consumption;
        this.correctionForId = correctionForId;
        this.correctedById = correctedById;
    }

    public TankFillingDTO() {
    }

    public DiscrepancyType getDiscrepancyType() {
        return discrepancyType;
    }

    public Integer getCorrectionForId() {
        return correctionForId;
    }

    public void setCorrectionForId(Integer correctionForId) {
        this.correctionForId = correctionForId;
    }

    public Integer getCorrectedById() {
        return correctedById;
    }

    public void setCorrectedById(Integer correctedById) {
        this.correctedById = correctedById;
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

    public Boolean isDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(Boolean discrepancy) {
        this.discrepancy = discrepancy;
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

    public Boolean getDiscrepancy() {
        return discrepancy;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        return "TankFillingDTO : " + this.getTankFillingId();
    }
}
