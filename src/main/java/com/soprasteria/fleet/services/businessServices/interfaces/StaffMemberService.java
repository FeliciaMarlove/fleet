package com.soprasteria.fleet.services.businessServices.interfaces;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;

import java.util.List;

public interface StaffMemberService {
    StaffMemberDTO read(Integer staffMemberId);
    List<StaffMemberDTO> read(String filter, String option);
    StaffMemberDTO update(StaffMemberDTO staffMemberDTO);
    List<CarDTO> getCarsOfStaffMember(Integer staffMemberId);
    CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate);
    CarDTO getCurrentCarOfStaffMember(Integer staffMemberId);
}
