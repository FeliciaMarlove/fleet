package com.soprasteria.fleet.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_employee_linking")
public class CarEmployeeLinking {
    @EmbeddedId
    private CarEmployeeCompositePK carEmployeeCompositePK;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "km_at_start")
    private Double kmAtStart;

    @Column(name = "km_at_end")
    private Double kmAtEnd;

    @Column(name = "ongoing")
    private boolean isOngoing;

    /**
     * Calculation of remaining transportation budget based on staff member monthly allowance and car monthly budget
     */
    @Column(name = "monthly_remaining_credit")
    private Double monthlyRemainingCredit;

    /**
     * Calculation of personal participation based on staff member monthly allowance and car monthly budget
     */
    @Column(name = "personal_part")
    private Double personalPart;

    @ManyToOne
    @MapsId("carPlate")
    @JoinColumn(name = "car_plate")
    private Car car;

    @ManyToOne
    @MapsId("staffMemberId")
    @JoinColumn(name = "staff_member_id")
    private StaffMember staffMember;

    /**
     * Set of 0-2 car inspections with max 1 init car inspection and 1 end (non-init) car inspection
     */
    @OneToMany(mappedBy = "carEmployeeLinking", targetEntity = CarInspection.class)
    private Set<CarInspection> inspections = new HashSet<>();
}
