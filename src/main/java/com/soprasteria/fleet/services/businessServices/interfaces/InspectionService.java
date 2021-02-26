package com.soprasteria.fleet.services.businessServices.interfaces;

import com.soprasteria.fleet.dto.InspectionDTO;

import java.util.List;

public interface InspectionService {
    InspectionDTO read(Integer inspectionId);
    List<InspectionDTO> readAll();
    List<InspectionDTO> readAllWhereCarIsDamaged();
    List<InspectionDTO> readAllByStaffMember(Integer staffMemberId);
    InspectionDTO create(InspectionDTO inspectionDTO);
    InspectionDTO update(InspectionDTO inspectionDTO);
}
