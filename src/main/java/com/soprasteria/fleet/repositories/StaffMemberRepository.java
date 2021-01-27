package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StaffMemberRepository extends CrudRepository<StaffMember, Integer> {
    @Query(
            value = "SELECT * FROM car c WHERE c.staff_id = ?1",
            nativeQuery = true)
    List<Car> selectCarWhereStaffIdIs(Integer staffId);

    @Query(
            value = "SELECT * FROM car c WHERE c.staff_id = ?1 AND c.ongoing = true",
            nativeQuery = true)
    Optional<Car> selectCarWhereStaffIdIsAndOngoingTrue(Integer staffId);
}
