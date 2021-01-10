package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Inspection;
import org.springframework.data.repository.CrudRepository;

public interface InspectionRepository extends CrudRepository<Inspection, Integer> {
}
