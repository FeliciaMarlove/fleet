package com.soprasteria.fleet.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_inspection")
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_inspection_generator")
    @SequenceGenerator(name = "car_inspection_generator", allocationSize = 1)
    @Column(name = "car_inspection_id")
    private Integer carInspectionId;

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Column(name = "inspection_by")
    private String expertisedBy;

    @Column(name = "is_damaged")
    private boolean isDamaged;

    @Column(name = "picture_1")
    private byte[] picture1;

    @Column(name = "picture_2")
    private byte[] picture2;

    @Column(name = "picture_3")
    private byte[] picture3;

    @Column(name = "inspection_report")
    private byte[] inspection_report;

    @OneToOne
    @JoinColumn(name = "car_plate", referencedColumnName = "car_plate", foreignKey = @ForeignKey(name = "FK_inspection_car"))
    private Car car;

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

    public String getExpertisedBy() {
        return expertisedBy;
    }

    public void setExpertisedBy(String expertisedBy) {
        this.expertisedBy = expertisedBy;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public byte[] getPicture1() {
        return picture1;
    }

    public void setPicture1(byte[] picture1) {
        this.picture1 = picture1;
    }

    public byte[] getPicture2() {
        return picture2;
    }

    public void setPicture2(byte[] picture2) {
        this.picture2 = picture2;
    }

    public byte[] getPicture3() {
        return picture3;
    }

    public void setPicture3(byte[] picture3) {
        this.picture3 = picture3;
    }

    public byte[] getInspection_report() {
        return inspection_report;
    }

    public void setInspection_report(byte[] inspection_report) {
        this.inspection_report = inspection_report;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
