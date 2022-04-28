package com.thecompany.fleet.services.businessServices.interfaces;

import com.thecompany.fleet.dto.CarDTO;
import com.thecompany.fleet.dto.StaffMemberDTO;

import java.util.List;

public interface StaffMemberService {
    StaffMemberDTO read(Integer staffMemberId);
    List<StaffMemberDTO> read(String filter, String option);
    StaffMemberDTO update(StaffMemberDTO staffMemberDTO);
    List<CarDTO> getCarsOfStaffMember(Integer staffMemberId);
    CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate);
    CarDTO getCurrentCarOfStaffMember(Integer staffMemberId);
}
