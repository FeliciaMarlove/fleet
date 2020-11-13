package com.soprasteria.fleet.models;

import javax.persistence.*;

@Entity
@Table(name = "staff_member")
public class StaffMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_member_generator")
    @SequenceGenerator(name = "staff_member_generator", allocationSize = 1)
    @Column(name = "staff_id")
    private Integer staffMemberId;

    @Column(name = "staff_name")
    private String staffLastName;

    @Column(name = "staff_firstname")
    private String staffFirstName;

    @Column(name = "has_car")
    private boolean hasCar;

    @Column(name = "language")
    private Language communicationLanguage;

    @ManyToOne(targetEntity = EmployeeLevel.class)
    @JoinColumn(name = "employee_level_id", referencedColumnName = "employee_level_id", foreignKey = @ForeignKey(name = "FK_staff_member_employee_level"))
    private EmployeeLevel employeeLevel;
}
