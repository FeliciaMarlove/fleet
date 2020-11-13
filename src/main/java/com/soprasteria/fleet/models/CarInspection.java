package com.soprasteria.fleet.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car_inspection")
public class CarInspection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_inspection_generator")
    @SequenceGenerator(name = "car_inspection_generator", allocationSize = 1)
    @Column(name = "car_inspection_id")
    private Integer carInspectionId;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate;

    @Column(name = "inspection_by")
    private String expertisedBy;

    @Column(name = "is_damaged")
    private boolean isDamaged;

    @Column(name = "is_new")
    private boolean isNew;
}
