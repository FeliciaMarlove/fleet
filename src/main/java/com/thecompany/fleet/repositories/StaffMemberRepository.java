package com.thecompany.fleet.repositories;

import com.thecompany.fleet.models.StaffMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

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
