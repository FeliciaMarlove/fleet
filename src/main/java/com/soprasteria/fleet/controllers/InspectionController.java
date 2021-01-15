package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.services.interfaces.InspectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inspection")
@CrossOrigin
public class InspectionController {
    private final InspectionService service;

    public InspectionController(InspectionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public InspectionDTO getInspection(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping
    public List<InspectionDTO> getAllInspection() {
        return service.readAll();
    }

    @GetMapping("/damaged")
    public List<InspectionDTO> getAllInspectionWhereDamaged() {
        return service.readAllWhereCarIsDamaged();
    }

    @GetMapping("/{staffId}")
    public List<InspectionDTO> getAllByStaffMember(@PathVariable("staffId") Integer staffId) {
        return service.readAllByStaffMember(staffId);
    }

    @PostMapping
    public InspectionDTO createInspection(@RequestBody InspectionDTO inspectionDTO) {
        return service.create(inspectionDTO);
    }

    @PutMapping
    public InspectionDTO updateInspection(@RequestBody InspectionDTO inspectionDTO) {
        return service.update(inspectionDTO);
    }
}
