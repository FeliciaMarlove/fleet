package com.soprasteria.fleet.models;

import com.soprasteria.fleet.models.primaryKeys.CarEmployeeCompositePK;

import javax.persistence.*;
import java.time.LocalDate;

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

    @OneToOne(mappedBy = "carEmployeeLinking", targetEntity = Inspection.class)
    private Inspection inspection;
}
