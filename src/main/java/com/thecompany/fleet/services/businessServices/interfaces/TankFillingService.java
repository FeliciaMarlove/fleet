package com.thecompany.fleet.services.businessServices.interfaces;

import com.thecompany.fleet.dto.TankFillingDTO;

import java.util.List;

public interface TankFillingService {
    TankFillingDTO read(Integer tankFillingId);
    List<TankFillingDTO> read(String filter, String option);
    TankFillingDTO create(TankFillingDTO tankFillingDTO);
    TankFillingDTO update(TankFillingDTO tankFillingDTO);
}
