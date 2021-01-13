package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.TankFillingDTO;

import java.util.List;

public interface TankFillingService {
    TankFillingDTO read(Integer tankFillingId);
    List<TankFillingDTO> readAll();
    TankFillingDTO create(TankFillingDTO tankFillingDTO);
}
