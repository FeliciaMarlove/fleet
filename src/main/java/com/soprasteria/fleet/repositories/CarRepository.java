package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, String> {
    @Query(
            value = "SELECT * FROM car c WHERE c.fuel = ?1",
            nativeQuery = true)
    List<Car> selectFromCarWhereFuelIs(Integer fuelOrdinal);

    @Query(
            value = "SELECT * FROM car c WHERE c.brand = ?1",
            nativeQuery = true)
    List<Car> selectFromCarWhereBrandIs(Integer brandOrdinal);
}
