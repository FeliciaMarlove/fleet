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

    /**
     * If car inspection isInit and car !isPool = isNew
     */
    @Column(name = "is_new")
    private boolean isNew;

    @Column(name = "picture_1")
    private byte[] picture1;

    @Column(name = "picture_2")
    private byte[] picture2;

    @Column(name = "picture_3")
    private byte[] picture3;

    @Column(name = "inspection_report")
    private byte[] inspection_report;

    @Column(name = "is_init")
    private boolean isInitInspection;

    @ManyToOne
    @JoinColumns( {
            @JoinColumn(name = "car_plate", referencedColumnName = "car_plate", foreignKey = @ForeignKey(name = "FK_inspection_car")),
            @JoinColumn(name = "staff_member_id", referencedColumnName = "staff_member_id", foreignKey = @ForeignKey(name = "FK_inspection_employee"))
    })
    private CarEmployeeLinking carEmployeeLinking;
}
