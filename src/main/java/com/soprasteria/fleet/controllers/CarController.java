package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
@CrossOrigin({"http://fleetfront.8f6e75564cde45eeb707.westeurope.aksapp.io"})
public final class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") String id) {
        return service.read(id);
    }

    @GetMapping("/{filter}/{option}")
    public List<CarDTO> getCars(@PathVariable("filter") String filter, @PathVariable("option") String option) {
        return service.read(filter, option);
    }

    @PostMapping
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return service.create(carDTO);
    }

    @PutMapping
    public CarDTO updateCar(@RequestBody CarDTO carDTO) {
        return service.update(carDTO);
    }
}
