package com.soprasteria.fleet.repositories;

import com.soprasteria.fleet.models.LeasingCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LeasingCompanyRepository extends CrudRepository<LeasingCompany, Integer> {
    @Query(
            value = "SELECT * FROM leasing_company l WHERE l.is_active = true",
            nativeQuery = true
    )
    List<LeasingCompany> selectFromLeasingCompanyWhereActiveTrue();
}
