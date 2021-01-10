package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {
}
