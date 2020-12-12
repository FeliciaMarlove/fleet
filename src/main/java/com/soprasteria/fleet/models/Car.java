package com.soprasteria.fleet.models;

import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car {
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

    @ManyToOne(targetEntity = LeasingCompany.class)
    @JoinColumn(name = "leasing_id", referencedColumnName = "leasing_id", foreignKey = @ForeignKey(name = "FK_car_leasing_company"))
    private LeasingCompany leasingCompany;

}
