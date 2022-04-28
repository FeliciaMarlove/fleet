package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StaffMemberRepository extends CrudRepository<StaffMember, Integer> {
    @Query(
            value = "SELECT * FROM staff_member s WHERE s.has_car = true",
            nativeQuery = true
    )
    List<StaffMember> selectFromStaffWhereCarTrue();

    @Query(
            value = "SELECT * FROM staff_member s WHERE s.has_car = false",
            nativeQuery = true
    )
    List<StaffMember> selectFromStaffWhereCarFalse();
}
