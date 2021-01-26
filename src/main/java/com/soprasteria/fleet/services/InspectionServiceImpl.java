package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.interfaces.InspectionService;
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
        return (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
    }

    @Override
    public List<InspectionDTO> readAll() {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        for (Inspection inspection: repository.findAll()) {
            inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO()));
        }
        return inspectionDTOS;
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
        StaffMember staffMember = staffMemberRepository.findById(staffMemberId).get();
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        List<Car> cars = staffMember.getCars();
        for (Car car: cars) {
            if (car.getInspection() != null) {
                inspectionDTOS.add((InspectionDTO) new DtoUtils().convertToDto(car.getInspection(), new InspectionDTO()));
            }
        }
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
        if (inspectionDTO.getInspectionReport() != null) {
            inspection.setInspectionReport(inspectionDTO.getInspectionReport().getBytes());
        }
        if (inspectionDTO.getPicture1() != null) {
            inspection.setPicture1(inspectionDTO.getPicture1().getBytes());
        }
        if (inspectionDTO.getPicture2() != null) {
            inspection.setPicture2(inspectionDTO.getPicture2().getBytes());
        }
        if (inspectionDTO.getPicture3() != null) {
            inspection.setPicture3(inspectionDTO.getPicture3().getBytes());
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
