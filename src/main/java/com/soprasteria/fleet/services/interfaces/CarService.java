package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO read(String plateNumber);
    List<CarDTO> readAll();
    CarDTO create(CarDTO carDTO);
    String delete(String plateNumber);
    CarDTO update(CarDTO carDTO);
}
