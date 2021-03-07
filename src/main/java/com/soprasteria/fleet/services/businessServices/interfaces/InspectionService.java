package com.soprasteria.fleet.services.businessServices.interfaces;

import com.soprasteria.fleet.dto.InspectionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface InspectionService {
    InspectionDTO read(Integer inspectionId);
    List<InspectionDTO> read(String filter, String option);
    InspectionDTO create(InspectionDTO inspectionDTO);
    InspectionDTO update(InspectionDTO inspectionDTO);
}