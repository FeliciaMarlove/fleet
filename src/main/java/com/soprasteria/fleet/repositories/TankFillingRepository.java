package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.TankFilling;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TankFillingRepository extends CrudRepository<TankFilling, Integer> {
    @Query(
            value = "SELECT * FROM tank_filling t WHERE t.is_discrepancy = true",
            nativeQuery = true)
    List<TankFilling> selectTankFillingWhereDiscrepancyIsTrue();
}
