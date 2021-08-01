package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

import java.time.LocalDateTime;

public final class InspectionDTO implements DTOEntity {
    private Integer carInspectionId;
    private LocalDateTime inspectionDate;
    private LocalDateTime sentDate;
    private String expertisedBy;
    private Boolean damaged;
    private String picturesFiles;
    private String inspectionReportFile;
    private String car;
    private Integer staffMemberId;

    public Integer getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(Integer staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    public Integer getCarInspectionId() {
        return carInspectionId;
    }

    public void setCarInspectionId(Integer carInspectionId) {
        this.carInspectionId = carInspectionId;
    }

    public LocalDateTime getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDateTime inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public String getExpertisedBy() {
        return expertisedBy;
    }

    public void setExpertisedBy(String expertisedBy) {
        this.expertisedBy = expertisedBy;
    }

    public Boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(Boolean damaged) {
        this.damaged = damaged;
    }

    public Boolean getDamaged() {
        return damaged;
    }

    public String getPicturesFiles() {
        return picturesFiles;
    }

    public void setPicturesFiles(String picturesFiles) {
        this.picturesFiles = picturesFiles;
    }

    public String getInspectionReportFile() {
        return inspectionReportFile;
    }

    public void setInspectionReportFile(String inspectionReportFile) {
        this.inspectionReportFile = inspectionReportFile;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public InspectionDTO(Integer carInspectionId, LocalDateTime inspectionDate, LocalDateTime sentDate, String expertisedBy, Boolean damaged, String picturesFiles, String inspectionReportFile, String car, Integer staffMemberId) {
        this.carInspectionId = carInspectionId;
        this.inspectionDate = inspectionDate;
        this.sentDate = sentDate;
        this.expertisedBy = expertisedBy;
        this.damaged = damaged;
        this.picturesFiles = picturesFiles;
        this.inspectionReportFile = inspectionReportFile;
        this.car = car;
        this.staffMemberId = staffMemberId;
    }

    public InspectionDTO() {
    }


}
