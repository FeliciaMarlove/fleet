package com.soprasteria.fleet.services.businessServices.interfaces;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.enums.FuelType;

import java.util.List;

public interface CarService {
    CarDTO read(String plateNumber);
    List<CarDTO> readAll();
    List<CarDTO> readAllActive();
    List<CarDTO> readAllArchived();
    CarDTO create(CarDTO carDTO);
    //String delete(String plateNumber);
    CarDTO update(CarDTO carDTO);
    List<CarDTO> filterByBrand(String brand);
    List<CarDTO> filterByFuel(FuelType fuelType);
}
