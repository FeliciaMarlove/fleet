package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.FilterDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailSenderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
@CrossOrigin
public class CarController {
    private final CarService service;

    public CarController(CarService service, EmailSenderService emailSenderService) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") String id) {
        return service.read(id);
    }

    @GetMapping
    public List<CarDTO> getAllCars(@RequestBody FilterDTO filterDTO) {
        return service.read(filterDTO.getFilter(), filterDTO.getOption());
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
