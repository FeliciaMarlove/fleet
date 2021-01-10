package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.StaffMemberDTO;

import java.util.List;

public interface StaffMemberService {
    StaffMemberDTO read(Integer staffMemberId);
    List<StaffMemberDTO> readAll();
}
