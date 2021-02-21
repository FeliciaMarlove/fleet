package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StaffMemberRepository extends CrudRepository<StaffMember, Integer> {

}
