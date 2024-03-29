package com.thecompany.fleet.controllers;

import com.thecompany.fleet.dto.LeasingCompanyDTO;
import com.thecompany.fleet.services.businessServices.interfaces.LeasingCompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/leasing")
public final class LeasingCompanyController {
    public final LeasingCompanyService service;

    public LeasingCompanyController(LeasingCompanyService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public LeasingCompanyDTO getLeasingCompany(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping("/{filter}/{option}")
    public List<LeasingCompanyDTO> getLeasingCompanies(@PathVariable("filter") String filter, @PathVariable("option") String option) {
        return service.read(filter, option);
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
