package com.soprasteria.fleet.models;

import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car implements Serializable {
    @Id
    @Column(name = "plate")
    private String plateNumber;

    @Column(name = "chassis_number")
    private Integer chassisNumber;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "kilometers")
    private Double kilometers;

    @Column(name = "brand")
    private Brand brand;

    @Column(name = "model")
    private String model;

    @Column(name = "fuel")
    private FuelType fuelType;

    @Column(name = "average_consumption")
    private Double averageConsumption;

    @Column(name = "archived")
    private boolean isArchived;

    @ManyToOne(targetEntity = LeasingCompany.class)
    @JoinColumn(name = "leasing_id", referencedColumnName = "leasing_id", foreignKey = @ForeignKey(name = "FK_car_leasing_company"))
    private LeasingCompany leasingCompany;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(Integer chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
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
        this.averageConsumption = averageConsumption;
    }

    public LeasingCompany getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(LeasingCompany leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public Car(String plateNumber, Integer chassisNumber, LocalDate registrationDate, Double kilometers, Brand brand, String model, FuelType fuelType, Double averageConsumption, LeasingCompany leasingCompany) {
        this.plateNumber = plateNumber;
        this.chassisNumber = chassisNumber;
        this.registrationDate = registrationDate;
        this.kilometers = kilometers;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.averageConsumption = averageConsumption;
        this.leasingCompany = leasingCompany;
        this.isArchived = false;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "plateNumber='" + plateNumber + '\'' +
                ", chassisNumber=" + chassisNumber +
                ", registrationDate=" + registrationDate +
                ", kilometers=" + kilometers +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", fuelType=" + fuelType +
                ", averageConsumption=" + averageConsumption +
                ", leasingCompany=" + leasingCompany.getLeasingCompanyName() +
                ", isArchived=" + isArchived +
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
