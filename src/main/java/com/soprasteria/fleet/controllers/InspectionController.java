package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.InspectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inspection")
@CrossOrigin({"http://localhost:4200", "http://fleetfront.8f6e75564cde45eeb707.westeurope.aksapp.io"})
public final class InspectionController {
    private final InspectionService service;

    public InspectionController(InspectionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public InspectionDTO getInspection(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping("/{filter}/{option}")
    public List<InspectionDTO> getInspections(@PathVariable("filter") String filter, @PathVariable("option") String option) {
        return service.read(filter, option);
    }

    @PostMapping
    public InspectionDTO createInspection(@RequestBody InspectionDTO inspectionDTO) {
        return service.create(inspectionDTO);
    }
}
