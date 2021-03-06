package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.FilterDTO;
import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.LeasingCompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/leasing")
@CrossOrigin
public class LeasingCompanyController {
    public final LeasingCompanyService service;

    public LeasingCompanyController(LeasingCompanyService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public LeasingCompanyDTO getLeasingCompany(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping
    public List<LeasingCompanyDTO> getLeasingCompanies(@RequestBody FilterDTO filterDTO) {
        return service.read(filterDTO.getFilter(), filterDTO.getOption());
    }

    @PostMapping
    public LeasingCompanyDTO createLeasingCompany(@RequestBody LeasingCompanyDTO leasingCompanyDTO) {
        return service.create(leasingCompanyDTO);
    }

    @PutMapping
    public LeasingCompanyDTO updateLeasingCompany(@RequestBody LeasingCompanyDTO leasingCompanyDTO) {
        return service.update(leasingCompanyDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteLeasingCompany(@PathVariable("id") Integer id) {
        return service.delete(id);
    }
}
