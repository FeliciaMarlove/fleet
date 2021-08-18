package com.soprasteria.fleet.models;

import com.soprasteria.fleet.models.enums.Brand;
import com.soprasteria.fleet.models.enums.FuelType;
import com.soprasteria.fleet.services.utilServices.interfaces.Sanitizer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "car")
public final class Car implements Serializable {
    @Id
    @Column(name = "plate")
    private String plateNumber;

    @Column(name = "kilometers", nullable = false)
    private Integer kilometers;

    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "fuel", nullable = false)
    private FuelType fuelType;

    @Column(name = "average_consumption")
    private Double averageConsumption;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "ongoing", nullable = false)
    private Boolean ongoing;

    @Column(name = "free_text")
    private String freeText;

    @ManyToOne(targetEntity = LeasingCompany.class)
    @JoinColumn(name = "leasing_id", nullable = false, referencedColumnName = "leasing_id", foreignKey = @ForeignKey(name = "FK_car_leasing_company"))
    private LeasingCompany leasingCompany;

    @ManyToOne(targetEntity = StaffMember.class)
    @JoinColumn(name = "staff_id", nullable = false, referencedColumnName = "staff_id", foreignKey = @ForeignKey(name = "FK_car_staff_member"))
    private StaffMember staffMember;

    @OneToOne(mappedBy = "car")
    private Inspection inspection;

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = Sanitizer.stripXSS(plateNumber);
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
        this.model = Sanitizer.stripXSS(model);
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

    public LeasingCompany getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(LeasingCompany leasingCompany) {
        this.leasingCompany = leasingCompany;
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
        this.freeText = Sanitizer.stripXSS(freeText);
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public Car(String plateNumber, Integer kilometers, Brand brand, String model, FuelType fuelType, Double averageConsumption, LocalDate startDate, String freeText, LeasingCompany leasingCompany, StaffMember staffMember) {
        this();
        this.plateNumber = Sanitizer.stripXSS(plateNumber);
        this.kilometers = kilometers;
        this.brand = brand;
        this.model = Sanitizer.stripXSS(model);
        this.fuelType = fuelType;
        this.averageConsumption = averageConsumption;
        this.startDate = startDate;
        this.freeText = Sanitizer.stripXSS(freeText);
        this.leasingCompany = leasingCompany;
        this.staffMember = staffMember;
    }

    public Car() {
        this.ongoing = true;
        this.kilometers = 0;
    }

    @Override
    public String toString() {
        return "Car{" +
                "plateNumber='" + plateNumber + '\'' +
                ", kilometers=" + kilometers +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", fuelType=" + fuelType +
                ", averageConsumption=" + averageConsumption +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", ongoing=" + ongoing +
                ", freeText='" + freeText + '\'' +
                ", leasingCompany=" + leasingCompany.getLeasingCompanyName() +
                ", staffMember=" + (staffMember != null ?staffMember.getStaffFirstName() + " " + staffMember.getStaffLastName() : "No staff member") +
                ", inspection=" + (inspection != null ? inspection.getCarInspectionId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return plateNumber.equals(car.plateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNumber);
    }
}
