package com.soprasteria.fleet.services.businessServices.interfaces;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;

import java.util.List;

public interface LeasingCompanyService {
    LeasingCompanyDTO read(Integer leasingCompanyId);
    List<LeasingCompanyDTO> read(String filter, String option);
    LeasingCompanyDTO create(LeasingCompanyDTO leasingCompanyDTO);
    String delete(Integer leasingCompanyId);
    LeasingCompanyDTO update(LeasingCompanyDTO leasingCompanyDTO);
}
