package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.services.interfaces.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
@CrossOrigin
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") String id) {
        return service.read(id);
    }

    @GetMapping
    public List<CarDTO> getAllCars() {
        return service.readAll();
    }

    @GetMapping("/active")
    public List<CarDTO> getAllCarsActive() {
        return service.readAllActive();
    }

    @GetMapping("/archived")
    public List<CarDTO> getAllCarsArchived() {
        return service.readAllArchived();
    }

    @PostMapping
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return service.create(carDTO);
    }

    @DeleteMapping("/{id}")
    public String archiveCar(@PathVariable("id") String id) {
        return service.delete(id);
    }

    @PutMapping
    public CarDTO updateCar(@RequestBody CarDTO carDTO) {
        return service.update(carDTO);
    }

    @GetMapping("/brand/{brand}")
    public List<CarDTO> getAllCarsByBrand(@PathVariable("brand") Brand brand) {
        return service.filterByBrand(brand);
    }

    @GetMapping("/fuel/{fuel}")
    public List<CarDTO> getAllCarsByBrand(@PathVariable("fuel") FuelType fuelType) {
        return service.filterByFuel(fuelType);
    }
}
