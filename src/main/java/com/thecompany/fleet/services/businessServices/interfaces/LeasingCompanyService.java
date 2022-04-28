package com.thecompany.fleet.services.businessServices.interfaces;

import com.thecompany.fleet.dto.LeasingCompanyDTO;

import java.util.List;

public interface LeasingCompanyService {
    LeasingCompanyDTO read(Integer leasingCompanyId);
    List<LeasingCompanyDTO> read(String filter, String option);
    LeasingCompanyDTO create(LeasingCompanyDTO leasingCompanyDTO);
    String delete(Integer leasingCompanyId);
    LeasingCompanyDTO update(LeasingCompanyDTO leasingCompanyDTO);
}
