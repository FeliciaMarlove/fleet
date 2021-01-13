package com.soprasteria.fleet.models;

import com.soprasteria.fleet.enums.DiscrepancyLevel;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.enums.FuelType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tank_filling")
public class TankFilling implements Serializable {
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
    private Boolean discrepancy;

    @Column(name = "discrepancy_level")
    private DiscrepancyLevel discrepancyLevel;

    @Column(name = "discrepancy_type")
    private DiscrepancyType discrepancyType;

    @Column(name = "date_time_filling")
    private LocalDateTime dateTimeFilling;

    @Column(name = "fuel")
    private FuelType fuelType;

    @Column(name = "liters")
    private Double liters;

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "plate", referencedColumnName = "plate", foreignKey = @ForeignKey(name = "FK_filling_car"))
    private Car car;

    public Integer getTankFillingId() {
        return tankFillingId;
    }

    public void setTankFillingId(Integer tankFillingId) {
        this.tankFillingId = tankFillingId;
    }

    public Integer getKmBefore() {
        return kmBefore;
    }

    public void setKmBefore(Integer kmBefore) {
        this.kmBefore = kmBefore;
    }

    public Integer getKmAfter() {
        return kmAfter;
    }

    public void setKmAfter(Integer kmAfter) {
        this.kmAfter = kmAfter;
    }

    public Boolean isDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(Boolean discrepancy) {
        this.discrepancy = discrepancy;
    }

    public DiscrepancyLevel getDiscrepancyLevel() {
        return discrepancyLevel;
    }

    public void setDiscrepancyLevel(DiscrepancyLevel discrepancyLevel) {
        this.discrepancyLevel = discrepancyLevel;
    }

    public DiscrepancyType getDiscrepancyType() {
        return discrepancyType;
    }

    public void setDiscrepancyType(DiscrepancyType discrepancyType) {
        this.discrepancyType = discrepancyType;
    }

    public LocalDateTime getDateTimeFilling() {
        return dateTimeFilling;
    }

    public void setDateTimeFilling(LocalDateTime dateTimeFilling) {
        this.dateTimeFilling = dateTimeFilling;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public TankFilling(Integer tankFillingId, Integer kmBefore, Integer kmAfter, Boolean discrepancy, DiscrepancyLevel discrepancyLevel, DiscrepancyType discrepancyType, LocalDateTime dateTimeFilling, FuelType fuelType, Double liters, Car car) {
        this.tankFillingId = tankFillingId;
        this.kmBefore = kmBefore;
        this.kmAfter = kmAfter;
        this.discrepancy = discrepancy;
        this.discrepancyLevel = discrepancyLevel;
        this.discrepancyType = discrepancyType;
        this.dateTimeFilling = dateTimeFilling;
        this.fuelType = fuelType;
        this.liters = liters;
        this.car = car;
    }

    public TankFilling() {
    }

    @Override
    public String toString() {
        return "TankFilling{" +
                "tankFillingId=" + tankFillingId +
                ", kmBefore=" + kmBefore +
                ", kmAfter=" + kmAfter +
                ", discrepancy=" + discrepancy +
                ", discrepancyLevel=" + discrepancyLevel +
                ", discrepancyType=" + discrepancyType +
                ", dateTimeFilling=" + dateTimeFilling +
                ", fuelType=" + fuelType +
                ", liters=" + liters +
                ", car=" + car.getPlateNumber() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TankFilling that = (TankFilling) o;
        return tankFillingId.equals(that.tankFillingId) ||
                (dateTimeFilling.equals(that.dateTimeFilling) &&
                car.equals(that.car));
    }

    @Override
    public int hashCode() {
        return Objects.hash(tankFillingId, dateTimeFilling, car);
    }
}
