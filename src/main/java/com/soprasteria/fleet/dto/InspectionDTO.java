package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

import java.time.LocalDateTime;

public final class InspectionDTO implements DTOEntity {
    private Integer carInspectionId;
    private LocalDateTime inspectionDate;
    private LocalDateTime sentDate;
    private String expertisedBy;
    private Boolean damaged;
    private String picturesFolder;
    private String inspectionReportFile;
    private String plateNumber;
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

    public String getPicturesFolder() {
        return picturesFolder;
    }

    public void setPicturesFolder(String picturesFolder) {
        this.picturesFolder = picturesFolder;
    }

    public String getInspectionReportFile() {
        return inspectionReportFile;
    }

    public void setInspectionReportFile(String inspectionReportFile) {
        this.inspectionReportFile = inspectionReportFile;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public InspectionDTO(Integer carInspectionId, LocalDateTime inspectionDate, LocalDateTime sentDate, String expertisedBy, Boolean damaged, String picturesFolder, String inspectionReportFile, String plateNumber, Integer staffMemberId) {
        this.carInspectionId = carInspectionId;
        this.inspectionDate = inspectionDate;
        this.sentDate = sentDate;
        this.expertisedBy = expertisedBy;
        this.damaged = damaged;
        this.picturesFolder = picturesFolder;
        this.inspectionReportFile = inspectionReportFile;
        this.plateNumber = plateNumber;
        this.staffMemberId = staffMemberId;
    }

    public InspectionDTO() {
    }

    @Override
    public String toString() {
        return "InspectionDTO{" +
                "carInspectionId=" + carInspectionId +
                ", inspectionDate=" + inspectionDate +
                ", sentDate=" + sentDate +
                ", expertisedBy='" + expertisedBy +
                ", damaged=" + damaged +
                ", picturesFolder=" + picturesFolder +
                ", inspectionReportFile=" + inspectionReportFile +
                ", plateNumber=" + plateNumber +
                ", staffMember=" + staffMemberId +
                '}';
    }
}
