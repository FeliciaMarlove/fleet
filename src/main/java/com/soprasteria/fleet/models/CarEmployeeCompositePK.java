package com.soprasteria.fleet.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarEmployeeCompositePK implements Serializable {
    @Column(name = "car_plate")
    private String carPlate;

    @Column(name = "staff_member_id")
    private Integer staffMemberId;
}
