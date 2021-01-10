package com.soprasteria.fleet.models.primaryKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarEmployeeCompositePK implements Serializable {
    @Column(name = "car_plate")
    private String carPlate;

    @Column(name = "staff_member_id")
    private Integer staffMemberId;

    public CarEmployeeCompositePK(String carPlate, Integer staffMemberId) {
        this.carPlate = carPlate;
        this.staffMemberId = staffMemberId;
    }

    public CarEmployeeCompositePK() {
    }

    @Override
    public String toString() {
        return "CarEmployeeCompositePK{" +
                "carPlate='" + carPlate + '\'' +
                ", staffMemberId=" + staffMemberId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEmployeeCompositePK that = (CarEmployeeCompositePK) o;
        return carPlate.equals(that.carPlate) &&
                staffMemberId.equals(that.staffMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carPlate, staffMemberId);
    }
}
