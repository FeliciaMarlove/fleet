package com.soprasteria.fleet.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "plate")
    private String plateNumber;

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

    @Column(name = "fuel_card")
    private Integer fuelCardNumber;

    @Column(name = "is_pool")
    private boolean isPool;

    @ManyToOne(targetEntity = CarCategory.class)
    @JoinColumn(name = "car_category_id", referencedColumnName = "car_category_id", foreignKey = @ForeignKey(name = "FK_car_car_category"))
    private CarCategory carCategory;

    @ManyToOne(targetEntity = LeasingCompany.class)
    @JoinColumn(name = "leasing_id", referencedColumnName = "leasing_id", foreignKey = @ForeignKey(name = "FK_car_leasing_company"))
    private LeasingCompany leasingCompany;

}
