package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.CarEmployeeLinking;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.primaryKeys.CarEmployeeCompositePK;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarEmployeeRepository extends CrudRepository<CarEmployeeLinking, CarEmployeeCompositePK> {
    Optional<CarEmployeeLinking> getCarEmployeeLinkingByCarAndIsOngoing(Car car, Boolean ongoing);
}
