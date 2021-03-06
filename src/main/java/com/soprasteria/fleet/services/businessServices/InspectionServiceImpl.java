package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.filters.InspectionFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.InspectionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspectionServiceImpl implements InspectionService {
    private final InspectionRepository repository;
    private final StaffMemberRepository staffMemberRepository;
    private final CarRepository carRepository;

    public InspectionServiceImpl(InspectionRepository repository, StaffMemberRepository staffMemberRepository, CarRepository carRepository) {
        this.repository = repository;
        this.staffMemberRepository = staffMemberRepository;
        this.carRepository = carRepository;
    }

    @Override
    public InspectionDTO read(Integer inspectionId) {
        Inspection inspection = repository.findById(inspectionId).get();
        return getInspectionDtoAndSetPlateNumber(inspection);
    }

    @Override
    public List<InspectionDTO> read(String filter, String option) {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        return filter(filter, option, inspectionDTOS);
    }

    @Override
    public InspectionDTO create(InspectionDTO inspectionDTO) {
        Inspection inspection = (Inspection) new DtoUtils().convertToEntity(new Inspection(), inspectionDTO);
        Car car = carRepository.findById(inspectionDTO.getPlateNumber()).get();
        inspection.setCar(car);

        car.setInspection(inspection);
        repository.save(inspection);
        carRepository.save(car);
        if (inspectionDTO.getSentDate() != null) {
            validateAndSendInspection(inspection);
        }
        return (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
    }

    private void validateAndSendInspection(Inspection inspection) {
        // TODO send e-mail puis mettre en bas avec les autres private
    }

    @Override
    public InspectionDTO update(InspectionDTO inspectionDTO) {
        Inspection inspection = (Inspection) new DtoUtils().convertToEntity(new Inspection(), inspectionDTO);
        if (inspectionDTO.isDamaged() != null) {
            inspection.setDamaged(inspectionDTO.isDamaged());
        }
        if (inspectionDTO.getExpertisedBy() != null) {
            inspection.setExpertisedBy(inspectionDTO.getExpertisedBy());
        }
        if (inspectionDTO.getInspectionReportFile() != null) {
            inspection.setInspectionReportFile(inspectionDTO.getInspectionReportFile());
        }
        if (inspectionDTO.getPicturesFolder() != null) {
            inspection.setPicturesFolder(inspectionDTO.getPicturesFolder());
        }
        if (inspectionDTO.getSentDate() != null) {
            inspection.setSentDate(inspectionDTO.getSentDate());
        }
        repository.save(inspection);
        if (inspectionDTO.getSentDate() != null) {
            validateAndSendInspection(inspection);
        }
        return (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<InspectionDTO> getAllInspections(List<InspectionDTO> inspectionDTOS) {
        for (Inspection inspection: repository.findAll()) {
            inspectionDTOS.add(getInspectionDtoAndSetPlateNumber(inspection));
        }
        return inspectionDTOS;
    }

    private List<InspectionDTO> getAllByStaffMember(String id, List<InspectionDTO> inspectionDTOS) {
        Integer staffMemberId = Integer.valueOf(id);
        List<Inspection> inspections = repository.selectInspectionWhereStaffIdIs(staffMemberId);
        inspections.forEach( inspection -> {
            inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(inspection, new CarDTO()));
        });
        return inspectionDTOS;
    }

    private List<InspectionDTO> getAllWhereCarIsDamaged(List<InspectionDTO> inspectionDTOS) {
        for (Inspection inspection: repository.findAll()) {
            if (inspection.isDamaged()) {
                inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO()));
            }
        }
        return inspectionDTOS;
    }

    private List<InspectionDTO> getAllWithDateBiggerThan(String date, List<InspectionDTO> inspectionDTOS) {
        LocalDateTime localDate = LocalDateTime.parse(date + "T00:00:00");
        return repository.selectInspectionWhereDateGreaterThan(localDate).stream().map(this::getInspectionDtoAndSetPlateNumber).collect(Collectors.toList());
    }

    private List<InspectionDTO> filter(String filter, String option, List<InspectionDTO> inspectionDTOS) {
        InspectionFilter inspectionFilter = InspectionFilter.valueOf(filter);
        try {
            switch (inspectionFilter) {
                case ALL: default: return getAllInspections(inspectionDTOS);
                case STAFF: return getAllByStaffMember(option, inspectionDTOS);
                case DAMAGED: return getAllWhereCarIsDamaged(inspectionDTOS);
                case DATEABOVE: return getAllWithDateBiggerThan(option, inspectionDTOS);
            }
        } catch (Exception e) {
            System.out.println(e);
            return getAllInspections(inspectionDTOS);
        }
    }

    // ------- DATA TRANSFORMATION -------

    /**
     * Transform Inspection into InspectionDTO and set PlateNumber
     * @param inspection The Inspection to transform
     * @return The Inspection DTO with Plate Number set
     */
    private InspectionDTO getInspectionDtoAndSetPlateNumber(Inspection inspection) {
        InspectionDTO inspectionDTO = (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
        inspectionDTO.setPlateNumber(inspection.getCar().getPlateNumber());
        return inspectionDTO;
    }

}
