package com.thecompany.fleet.controllers;

import com.thecompany.fleet.dto.CarDTO;
import com.thecompany.fleet.dto.StaffMemberDTO;
import com.thecompany.fleet.services.businessServices.interfaces.StaffMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/staff")
public final class StaffMemberController {
    private final StaffMemberService service;

    public StaffMemberController(StaffMemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public StaffMemberDTO getStaffMember(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping("/{filter}/{option}")
    public List<StaffMemberDTO> getStaff(@PathVariable("filter") String filter, @PathVariable("option") String option) {
        return service.read(filter, option);
    }

    @PutMapping
    public StaffMemberDTO updateStaffMember(@RequestBody StaffMemberDTO staffMemberDTO) {
        return service.update(staffMemberDTO);
    }

    @GetMapping("{id}/cars")
    public List<CarDTO> getCarsOfStaff(@PathVariable("id") Integer id) {
        return service.getCarsOfStaffMember(id);
    }

    @GetMapping("{id}/car")
    public CarDTO getCurrentCarOfStaff(@PathVariable("id") Integer id) {
        return service.getCurrentCarOfStaffMember(id);
    }

    @PutMapping("/{id}/car")
    public CarDTO setCarOfStaff(@PathVariable("id") Integer id, @RequestBody String carId) {
        return service.setCarOfStaffMember(id, carId);
    }
}
