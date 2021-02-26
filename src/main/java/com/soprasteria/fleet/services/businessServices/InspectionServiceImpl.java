package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.InspectionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<InspectionDTO> readAll() {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        for (Inspection inspection: repository.findAll()) {
            inspectionDTOS.add(getInspectionDtoAndSetPlateNumber(inspection));
        }
        return inspectionDTOS;
    }

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

    @Override
    public List<InspectionDTO> readAllWhereCarIsDamaged() {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        for (Inspection inspection: repository.findAll()) {
            if (inspection.isDamaged()) {
                inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO()));
            }
        }
        return inspectionDTOS;
    }

    @Override
    public List<InspectionDTO> readAllByStaffMember(Integer staffMemberId) {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        List<Inspection> inspections = repository.selectInspectionWhereStaffIdIs(staffMemberId);
        inspections.forEach( inspection -> {
            inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(inspection, new CarDTO()));
        });
        return inspectionDTOS;
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
        // TODO send e-mail
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
}
