package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends CrudRepository<Inspection, Integer> {
    @Query(
            value = "SELECT i FROM car_inspection i JOIN car c ON i.car_plate = c.plate WHERE c.staff_id = ?1",
            nativeQuery = true)
    List<Inspection> selectInspectionWhereStaffIdIs(Integer staffId);

    @Query(
            value = "SELECT * FROM car_inspection c WHERE c.inspection_date > ?1",
            nativeQuery = true)
    List<Inspection> selectInspectionWhereDateGreaterThan(LocalDateTime date);

    @Query(
            value = "SELECT * FROM car_inspection c WHERE c.is_damaged = true",
            nativeQuery = true)
    List<Inspection> selectInspectionWhereIsDamagedTrue();

    @Query(
            value = "SELECT * FROM car_inspection c WHERE c.car_plate = ?1",
            nativeQuery = true)
    Optional<Inspection> selectInspectionWhereCarPlateIs(String carPlate);
}
