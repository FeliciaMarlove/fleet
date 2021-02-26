package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/staff")
@CrossOrigin
public class StaffMemberController {
    private final StaffMemberService service;

    public StaffMemberController(StaffMemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public StaffMemberDTO getStaffMember(@PathVariable("id") Integer id) {
        return service.read(id);
    }

    @GetMapping
    public List<StaffMemberDTO> getAllStaff() {
        return service.readAll();
    }

    @GetMapping("/withcar")
    public List<StaffMemberDTO> getAllStaffWithCar() {
        return service.readAllWithCar();
    }

    @GetMapping("/withoutcar")
    public List<StaffMemberDTO> getAllStaffWithoutCar() {
        return service.readAllWithoutCar();
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
