package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;

import java.util.List;

public interface LeasingCompanyService {
    LeasingCompanyDTO read(Integer leasingCompanyId);
    List<LeasingCompanyDTO> readAll();
    LeasingCompanyDTO create(LeasingCompanyDTO leasingCompanyDTO);
    String delete(Integer leasingCompanyId);
    LeasingCompanyDTO update(LeasingCompanyDTO leasingCompanyDTO);
}
