package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.TankFillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/fillup")
public final class TankFillingController {
    private final TankFillingService service;

    public TankFillingController(TankFillingService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TankFillingDTO getFillup(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping("/{filter}/{option}")
    public List<TankFillingDTO> getFillups(@PathVariable("filter") String filter, @PathVariable("option") String option) {
        return service.read(filter, option);
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
