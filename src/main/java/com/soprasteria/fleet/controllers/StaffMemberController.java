package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.services.interfaces.StaffMemberService;
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

    @PutMapping
    public StaffMemberDTO updateStaffMember(@RequestBody StaffMemberDTO staffMemberDTO) {
        return service.update(staffMemberDTO);
    }
}
