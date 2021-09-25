package com.soprasteria.fleet.models;

import com.soprasteria.fleet.services.utilServices.interfaces.Sanitizer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "car_inspection")
public final class Inspection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_inspection_generator")
    @SequenceGenerator(name = "car_inspection_generator", allocationSize = 1, initialValue = 100)
    @Column(name = "car_inspection_id")
    private Integer carInspectionId;

    @Column(name = "inspection_date", nullable = false)
    private LocalDateTime inspectionDate;

    @Column(name= "sent_date", nullable = false)
    private LocalDateTime sentDate;

    @Column(name = "inspection_by", nullable = false)
    private String expertisedBy;

    @Column(name = "is_damaged")
    private boolean damaged;

    @Column(name = "pictures", length = 2500)
    private String picturesFiles;

    @Column(name = "inspection_report", length = 2500)
    private String inspectionReportFile;

    @OneToOne
    @JoinColumn(name = "car_plate", nullable = false, referencedColumnName = "plate", foreignKey = @ForeignKey(name = "FK_inspection_car"))
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
        this.expertisedBy = Sanitizer.stripXSS(expertisedBy);
    }

    public Boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(Boolean damaged) {
        if (damaged == null) {
            this.damaged = false;
        } else {
            this.damaged = damaged;
        }
    }

    public String getPicturesFiles() {
        return picturesFiles;
    }

    public void setPicturesFiles(String picturesFiles) {
        this.picturesFiles = Sanitizer.stripXSS(picturesFiles);
    }

    public String getInspectionReportFile() {
        return inspectionReportFile;
    }

    public void setInspectionReportFile(String inspectionReportFile) {
        this.inspectionReportFile = Sanitizer.stripXSS(inspectionReportFile);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public Inspection(Integer carInspectionId, LocalDateTime inspectionDate, LocalDateTime sentDate, String expertisedBy, Boolean damaged, String picturesFiles, String inspectionReportFile, Car car) {
        this.carInspectionId = carInspectionId;
        this.inspectionDate = inspectionDate;
        this.sentDate = sentDate;
        this.expertisedBy = Sanitizer.stripXSS(expertisedBy);
        this.damaged = damaged == null ? false : damaged;
        this.picturesFiles = Sanitizer.stripXSS(picturesFiles);
        this.inspectionReportFile = Sanitizer.stripXSS(inspectionReportFile);
        this.car = car;
    }

    public Inspection() {
    }

    @Override
    public String toString() {
        return "Inspection : " + this.getCarInspectionId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inspection that = (Inspection) o;
        return carInspectionId.equals(that.carInspectionId) ||
                (inspectionDate.equals(that.inspectionDate) &&
                car.equals(that.car));
    }

    @Override
    public int hashCode() {
        return Objects.hash(carInspectionId, inspectionDate);
    }
}
