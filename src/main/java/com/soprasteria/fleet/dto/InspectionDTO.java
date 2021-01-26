package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

import java.time.LocalDateTime;

public class InspectionDTO implements DTOEntity {
    private Integer carInspectionId;
    private LocalDateTime inspectionDate;
    private LocalDateTime sentDate;
    private String expertisedBy;
    private Boolean damaged;
    /*
    DOC
    dealing with byte[] and Strings
    (check if it's the way to do it)
    field might be a byte[] from front?
    check the sent data when front end is working
    https://stackoverflow.com/questions/17498265/java-store-byte-array-as-string-in-db-and-create-byte-array-using-string-value/29973296
    https://stackoverflow.com/questions/1295934/java-strings-storing-byte-arrays#:~:text=Take%20the%20byte%20array%20data,)%20%7B%20String%20hex%20%3D%20String.
     */
    private String picture1;
    private String picture2;
    private String picture3;
    private String inspectionReport;
    private String plateNumber;

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

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getInspectionReport() {
        return inspectionReport;
    }

    public void setInspectionReport(String inspectionReport) {
        this.inspectionReport = inspectionReport;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public InspectionDTO(Integer carInspectionId, LocalDateTime inspectionDate, LocalDateTime sentDate, String expertisedBy, Boolean damaged, String picture1, String picture2, String picture3, String inspectionReport, String plateNumber) {
        this.carInspectionId = carInspectionId;
        this.inspectionDate = inspectionDate;
        this.sentDate = sentDate;
        this.expertisedBy = expertisedBy;
        this.damaged = damaged;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.inspectionReport = inspectionReport;
        this.plateNumber = plateNumber;
    }

    public InspectionDTO() {
    }

    @Override
    public String toString() {
        return "InspectionDTO{" +
                "carInspectionId=" + carInspectionId +
                ", inspectionDate=" + inspectionDate +
                ", sentDate=" + sentDate +
                ", expertisedBy='" + expertisedBy + '\'' +
                ", isDamaged=" + damaged +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                ", inspectionReport='" + inspectionReport + '\'' +
                ", carPlate='" + plateNumber + '\'' +
                '}';
    }
}
