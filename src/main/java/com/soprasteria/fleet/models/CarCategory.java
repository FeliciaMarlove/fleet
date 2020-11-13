package com.soprasteria.fleet.models;

import javax.persistence.*;

@Entity
@Table(name = "car_category")
public class CarCategory {
    @Id
    @Column(name = "car_category_id")
    private String carCategory;

    @Column(name = "monthly_car_category_allowance")
    private Double monthlyBudgetAllowance;
}
