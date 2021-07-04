package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.models.enums.filters.InspectionFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.InspectionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspectionServiceImpl implements InspectionService {
    private final InspectionRepository repository;
    private final CarRepository carRepository;

    public InspectionServiceImpl(InspectionRepository repository, CarRepository carRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
    }

    @Override
    public InspectionDTO read(Integer inspectionId) {
        Inspection inspection = repository.findById(inspectionId).orElseThrow(() -> new FleetItemNotFoundException("No inspection found with id " + inspectionId));
        return getInspectionDtoAndSetPlateNumberAndStaffId(inspection);
    }

    @Override
    public List<InspectionDTO> read(String filter, String option) {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        return filter(filter, option, inspectionDTOS);
    }

    @Override
    public InspectionDTO create(InspectionDTO inspectionDTO) {
        Inspection inspection = (Inspection) new DtoUtils().convertToEntity(new Inspection(), inspectionDTO);
        Car car = carRepository.findById(inspectionDTO.getPlateNumber()).orElseThrow(() -> new FleetItemNotFoundException("No car found with plate number " + inspectionDTO.getPlateNumber()));
        inspection.setCar(car);

        car.setInspection(inspection);
        repository.save(inspection);
        carRepository.save(car);

        sendInspection(inspection);
        return (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
    }

    private void sendInspection(Inspection inspection) {
        try {
            // TODO send e-mail puis mettre en bas avec les autres private
        } catch (Exception e) {
            throw new FleetGenericException("Sending e-mail failed for inspection " + inspection.getCarInspectionId());
        }
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<InspectionDTO> getAllInspections(List<InspectionDTO> inspectionDTOS) {
        for (Inspection inspection: repository.findAll()) {
            inspectionDTOS.add(getInspectionDtoAndSetPlateNumberAndStaffId(inspection));
        }
        return inspectionDTOS;
    }

    private List<InspectionDTO> getAllByStaffMember(String id) {
        try {
            Integer staffMemberId = Integer.valueOf(id);
            return repository.selectInspectionWhereStaffIdIs(staffMemberId).stream().map(this::getInspectionDtoAndSetPlateNumberAndStaffId).collect(Collectors.toList());
        } catch (Exception e) {
            throw new FleetGenericException("Failed to convert staff id to integer " + id);
        }
    }

    private List<InspectionDTO> getAllWhereCarIsDamaged() {
        return repository.selectInspectionWhereIsDamagedTrue().stream().map(this::getInspectionDtoAndSetPlateNumberAndStaffId).collect(Collectors.toList());
    }

    private List<InspectionDTO> getAllWithDateBiggerThan(String date) {
        try {
            LocalDateTime localDate = LocalDateTime.parse(date + "T00:00:00");
            return repository.selectInspectionWhereDateGreaterThan(localDate).stream().map(this::getInspectionDtoAndSetPlateNumberAndStaffId).collect(Collectors.toList());
        } catch (Exception e) {
            throw new FleetGenericException("Failed to convert parse date " + date);
        }
    }

    private List<InspectionDTO> filter(String filter, String option, List<InspectionDTO> inspectionDTOS) {
        try {
            InspectionFilter inspectionFilter = InspectionFilter.valueOf(filter);
            switch (inspectionFilter) {
                case ALL: default: return getAllInspections(inspectionDTOS);
                case STAFF: return getAllByStaffMember(option);
                case DAMAGED: return getAllWhereCarIsDamaged();
                case DATE_ABOVE: return getAllWithDateBiggerThan(option);
            }
        } catch (Exception e) {
            // TODO log
            return getAllInspections(inspectionDTOS);
        }
    }

    // ------- DATA TRANSFORMATION -------

    /**
     * Transform Inspection into InspectionDTO and set PlateNumber and staff member Id
     * @param inspection The Inspection to transform
     * @return The Inspection DTO with Plate Number set
     */
    private InspectionDTO getInspectionDtoAndSetPlateNumberAndStaffId(Inspection inspection) {
        InspectionDTO inspectionDTO = (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
        inspectionDTO.setPlateNumber(inspection.getCar().getPlateNumber());
        inspectionDTO.setStaffMemberId(inspection.getCar().getStaffMember().getStaffMemberId());
        return inspectionDTO;
    }

}
