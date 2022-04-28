package com.thecompany.fleet.repositories;

import com.thecompany.fleet.models.TankFilling;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TankFillingRepository extends CrudRepository<TankFilling, Integer> {
    @Query(
            value = "SELECT * FROM tank_filling t WHERE t.is_discrepancy = true",
            nativeQuery = true)
    List<TankFilling> selectTankFillingWhereDiscrepancyIsTrue();

    @Query(
            value = "SELECT * FROM tank_filling t WHERE t.date_time_filling > ?1",
            nativeQuery = true)
    List<TankFilling> selectFillupWhereDateGreaterThan(LocalDateTime date);
    @Query(
            value = "SELECT * FROM tank_filling t WHERE t.is_discrepancy = true AND t.corrected_by IS NULL",
            nativeQuery = true)
    List<TankFilling> selectFillupWhereWhereDiscrepancyIsTrueAndCorrectedByIsNull();
}
