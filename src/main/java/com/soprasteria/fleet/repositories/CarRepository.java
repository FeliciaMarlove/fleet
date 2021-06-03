package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, String> {
    @Query(
            value = "SELECT * FROM car c WHERE c.fuel = ?1",
            nativeQuery = true)
    List<Car> selectFromCarWhereFuelIs(Integer fuelOrdinal);

    @Query(
            value = "SELECT * FROM car c WHERE c.brand = ?1",
            nativeQuery = true)
    List<Car> selectFromCarWhereBrandIs(Integer brandOrdinal);

    @Query(
            value = "SELECT * FROM car c WHERE c.ongoing = true",
            nativeQuery = true)
    List<Car> selectFromCarWhereOngoing();

    @Query(
            value = "SELECT * FROM car c WHERE c.ongoing = false",
            nativeQuery = true)
    List<Car> selectFromCarWhereNotOngoing();

    @Query(
            value = "SELECT * FROM car c WHERE c.staff_id = ?1",
            nativeQuery = true)
    List<Car> selectCarWhereStaffIdIs(Integer staffId);

    @Query(
            value = "SELECT * FROM car c WHERE c.staff_id = ?1 AND c.ongoing = true LIMIT 1",
            nativeQuery = true)
    Optional<Car> selectCarWhereStaffIdIsAndOngoingTrue(Integer staffId);
}
