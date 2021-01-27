package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InspectionRepository extends CrudRepository<Inspection, Integer> {
    @Query(
            value = "SELECT i FROM car_inspection i JOIN car c ON i.car_plate = c.plate WHERE c.staff_id = ?1",
            nativeQuery = true)
    List<Inspection> selectInspectionWhereStaffIdIs(Integer staffId);
}
