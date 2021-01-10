package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

public class CarEmployeeDTO implements DTOEntity {
    private Integer plateNumber;
    private Integer staffMemberId;
    private Double kmAtEnd;
    private boolean isOngoing;
    private String freeText;

    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(Integer plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(Integer staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    public Double getKmAtEnd() {
        return kmAtEnd;
    }

    public void setKmAtEnd(Double kmAtEnd) {
        this.kmAtEnd = kmAtEnd;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public CarEmployeeDTO(Integer plateNumber, Integer staffMemberId, Double kmAtEnd, boolean isOngoing, String freeText) {
        this.plateNumber = plateNumber;
        this.staffMemberId = staffMemberId;
        this.kmAtEnd = kmAtEnd;
        this.isOngoing = isOngoing;
        this.freeText = freeText;
    }

    public CarEmployeeDTO() {
    }

    @Override
    public String toString() {
        return "CarEmployeeDTO{" +
                "plateNumber=" + plateNumber +
                ", staffMemberId=" + staffMemberId +
                ", kmAtEnd=" + kmAtEnd +
                ", isOngoing=" + isOngoing +
                ", freeText='" + freeText + '\'' +
                '}';
    }
}
