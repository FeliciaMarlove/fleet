package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;

import java.util.List;

public interface StaffMemberService {
    StaffMemberDTO read(Integer staffMemberId);
    List<StaffMemberDTO> readAll();
    List<StaffMemberDTO> readAllWithCar();
    List<StaffMemberDTO> readAllWithoutCar();
    StaffMemberDTO update(StaffMemberDTO staffMemberDTO);
    List<CarDTO> getCarsOfStaffMember(Integer staffMemberId);
    CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate);
    CarDTO getCurrentCarOfStaffMember(Integer staffMemberId);
}
