package com.soprasteria.fleet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "plate")
    private String plateNumber;

    @Column(name = "immatriculation_date")
    private LocalDate immatriculationDate;

    @Column(name = "kilometers")
    private Double kilometers;

    @Column(name = "brand")
    private Brand brand;

    @Column(name = "model")
    private String model;

    @Column(name = "fuel")
    private FuelType fuelType;

    @Column(name = "fuel_card")
    private Integer fuelCardNumber;

    @Column(name = "isPool")
    private boolean isPool;
}
