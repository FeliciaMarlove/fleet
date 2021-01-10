package com.soprasteria.fleet.models;

import com.soprasteria.fleet.models.primaryKeys.CarEmployeeCompositePK;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "car_employee_linking")
public class CarEmployeeLinking implements Serializable {
    @EmbeddedId
    private CarEmployeeCompositePK carEmployeeCompositePK;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "km_at_end")
    private Double kmAtEnd;

    @Column(name = "ongoing")
    private boolean isOngoing;

    @ManyToOne
    @MapsId("carPlate")
    @JoinColumn(name = "car_plate")
    private Car car;

    @ManyToOne
    @MapsId("staffMemberId")
    @JoinColumn(name = "staff_member_id")
    private StaffMember staffMember;

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

    public CarEmployeeLinking(LocalDate startDate, LocalDate endDate, boolean isOngoing, Car car, StaffMember staffMember) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOngoing = isOngoing;
        this.car = car;
        this.staffMember = staffMember;
        this.carEmployeeCompositePK = new CarEmployeeCompositePK(car.getPlateNumber(), staffMember.getStaffMemberId());
    }

    public CarEmployeeLinking() {
    }

    @Override
    public String toString() {
        return "CarEmployeeLinking{" +
                "carEmployeeCompositePK=" + carEmployeeCompositePK +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", kmAtEnd=" + kmAtEnd +
                ", isOngoing=" + isOngoing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEmployeeLinking that = (CarEmployeeLinking) o;
        return carEmployeeCompositePK.equals(that.carEmployeeCompositePK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carEmployeeCompositePK);
    }
}