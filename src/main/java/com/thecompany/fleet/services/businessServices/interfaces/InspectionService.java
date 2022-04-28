package com.thecompany.fleet.services.businessServices.interfaces;

import com.thecompany.fleet.dto.InspectionDTO;

import java.util.List;

public interface InspectionService {
    InspectionDTO read(Integer inspectionId);
    List<InspectionDTO> read(String filter, String option);
    InspectionDTO create(InspectionDTO inspectionDTO);
}
