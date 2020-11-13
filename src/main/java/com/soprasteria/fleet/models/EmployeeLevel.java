package com.soprasteria.fleet.models;

import javax.persistence.*;

@Entity
@Table(name = "employee_leve")
public class EmployeeLevel {
    @Id
    @Column(name = "employee_level_id")
    private String employeeLevel;

    @Column(name = "monthly_employee_car_allowance")
    private Double monthlyCarBudgetAllowance;
}
