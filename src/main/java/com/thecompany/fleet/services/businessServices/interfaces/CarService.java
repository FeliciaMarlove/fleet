package com.thecompany.fleet.services.businessServices.interfaces;

import com.thecompany.fleet.dto.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO read(String plateNumber);
    List<CarDTO> read(String filter, String option);
    CarDTO create(CarDTO carDTO);
    CarDTO update(CarDTO carDTO);
}
