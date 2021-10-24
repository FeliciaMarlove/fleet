package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.TankFillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/fillup")
public final class TankFillingController {

    @RestController
    @RequestMapping(value = "/external/fillup")
    public final class TankFillingExternalController {
        @PostMapping
        public TankFillingDTO createFillup(@RequestBody TankFillingDTO tankFillingDTO) {
            return service.create(tankFillingDTO);
        }

        @GetMapping
        public String test() {
            return "PAN";
        }
    }

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

    @PostMapping("/correction")
    public TankFillingDTO updateFillup(@RequestBody TankFillingDTO tankFillingDTO) {
        return service.update(tankFillingDTO);
    }
}
