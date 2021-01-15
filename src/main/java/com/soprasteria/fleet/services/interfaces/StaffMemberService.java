package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;

import java.util.List;
import java.util.Optional;

public interface StaffMemberService {
    StaffMemberDTO read(Integer staffMemberId);
    List<StaffMemberDTO> readAll();
    List<StaffMemberDTO> readAllWithCar();
    StaffMemberDTO update(StaffMemberDTO staffMemberDTO);
    List<CarDTO> getCarsOfStaffMember(Integer staffMemberId);
    StaffMemberDTO setCarOfStaffMember(Integer staffMemberId, String carPlate);
    CarDTO getCurrentCarOfStaffMember(Integer staffMemberId);
}
