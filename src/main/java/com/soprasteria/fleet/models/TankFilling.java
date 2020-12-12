package com.soprasteria.fleet.models;

import com.soprasteria.fleet.enums.DiscrepancyLevel;
import com.soprasteria.fleet.enums.FuelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tank_filling")
public class TankFilling {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filling_generator")
    @SequenceGenerator(name = "filling_generator", allocationSize = 1)
    @Column(name = "tank_filling_id")
    private Integer tankFillingId;

    @Column(name = "km_before")
    private Integer kmBefore;

    @Column(name = "km_after")
    private Integer kmAfter;

    @Column(name = "is_discrepancy")
    private boolean discrepancy;

    @Column(name = "discrepancy_level")
    private DiscrepancyLevel discrepancyLevel;

    @Column(name = "date_time_filling")
    private LocalDateTime dateTimeFilling;

    @Column(name = "fuel")
    private FuelType fuelType;

    @Column(name = "liters")
    private Double liters;

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "plate", referencedColumnName = "plate", foreignKey = @ForeignKey(name = "FK_filling_car"))
    private Car car;
}
