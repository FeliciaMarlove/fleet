package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.FilterDTO;
import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.TankFillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/fillup")
@CrossOrigin
public class TankFillingController {
    private final TankFillingService service;

    public TankFillingController(TankFillingService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TankFillingDTO getFillup(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping
    public List<TankFillingDTO> getFillups(@RequestBody FilterDTO filterDTO) {
        return service.read(filterDTO.getFilter(), filterDTO.getOption());
    }

    @PostMapping
    public TankFillingDTO createFillup(@RequestBody TankFillingDTO tankFillingDTO) {
        return service.create(tankFillingDTO);
    }

    @PostMapping("/correction")
    public TankFillingDTO updateFillup(@RequestBody TankFillingDTO tankFillingDTO) {
        return service.update(tankFillingDTO);
    }
}
