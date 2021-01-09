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

    public CarEmployeeCompositePK getCarEmployeeCompositePK() {
        return carEmployeeCompositePK;
    }

    public void setCarEmployeeCompositePK(CarEmployeeCompositePK carEmployeeCompositePK) {
        this.carEmployeeCompositePK = carEmployeeCompositePK;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getKmAtStart() {
        return kmAtStart;
    }

    public void setKmAtStart(Double kmAtStart) {
        this.kmAtStart = kmAtStart;
    }

    public Double getKmAtEnd() {
        return kmAtEnd;
    }

    public void setKmAtEnd(Double kmAtEnd) {
        this.kmAtEnd = kmAtEnd;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public Double getMonthlyRemainingCredit() {
        return monthlyRemainingCredit;
    }

    public void setMonthlyRemainingCredit(Double monthlyRemainingCredit) {
        this.monthlyRemainingCredit = monthlyRemainingCredit;
    }

    public Double getPersonalPart() {
        return personalPart;
    }

    public void setPersonalPart(Double personalPart) {
        this.personalPart = personalPart;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }
}
