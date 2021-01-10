package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.CarEmployeeLinking;
import com.soprasteria.fleet.models.primaryKeys.CarEmployeeCompositePK;
import org.springframework.data.repository.CrudRepository;

public interface CarEmployeeRepository extends CrudRepository<CarEmployeeLinking, CarEmployeeCompositePK> {
}
