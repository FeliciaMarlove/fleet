package com.thecompany.fleet.controllers;

import com.thecompany.fleet.dto.InspectionDTO;
import com.thecompany.fleet.services.businessServices.interfaces.InspectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inspection")
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
