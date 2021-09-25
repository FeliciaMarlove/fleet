package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.InspectionDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.enums.filters.InspectionFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.InspectionService;
import com.soprasteria.fleet.services.utilServices.AzureBlobLoggingServiceImpl;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailComposerService;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailSenderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InspectionServiceImpl implements InspectionService {
    private final AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl;
    private final InspectionRepository repository;
    private final CarRepository carRepository;
    private final EmailSenderService emailSenderService;
    private final EmailComposerService emailComposerService;

    public InspectionServiceImpl(AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl, InspectionRepository repository, CarRepository carRepository, EmailSenderService emailSenderService, EmailComposerService emailComposerService) {
        this.azureBlobLoggingServiceImpl = azureBlobLoggingServiceImpl;
        this.repository = repository;
        this.carRepository = carRepository;
        this.emailSenderService = emailSenderService;
        this.emailComposerService = emailComposerService;
    }

    @Override
    public InspectionDTO read(Integer inspectionId) {
        Optional<Inspection> optionalInspection = repository.findById(inspectionId);
        if (optionalInspection.isPresent()) {
            return getInspectionDtoAndSetPlateNumberAndStaffId(optionalInspection.get());
        } else {
            azureBlobLoggingServiceImpl.writeToLoggingFile("No inspection found with id " + inspectionId);
        }
        return null;
    }

    @Override
    public List<InspectionDTO> read(String filter, String option) {
        List<InspectionDTO> inspectionDTOS = new ArrayList<>();
        return filter(filter, option, inspectionDTOS);
    }

    @Override
    @Transactional
    public InspectionDTO create(InspectionDTO inspectionDTO) throws FleetItemNotFoundException {
        Inspection inspection = (Inspection) new DtoUtils().convertToEntity(new Inspection(), inspectionDTO);
        inspection.setCar(carRepository.findById(inspectionDTO.getPlateNumber()).get());
        repository.save(inspection);
        Optional<Car> optionalCar = carRepository.findById(inspectionDTO.getPlateNumber());
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            inspection.setCar(car);
            car.setInspection(inspection);
            carRepository.save(car);
            StaffMember staffMember = car.getStaffMember();
            sendInspection(staffMember, inspection);
        } else {
            azureBlobLoggingServiceImpl.writeToLoggingFile("Saving inspection with ID " + inspection.getCarInspectionId()+ ". No car found with plate number " + inspectionDTO.getPlateNumber() + ". Inspection was not sent.");
        }
        repository.save(inspection);

        return (InspectionDTO) new DtoUtils().convertToDto(inspection, new InspectionDTO());
    }



    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<InspectionDTO> getAllInspections(List<InspectionDTO> inspectionDTOS) {
        for (Inspection inspection: repository.findAll()) {
            inspectionDTOS.add(getInspectionDtoAndSetPlateNumberAndStaffId(inspection));
        }
        return inspectionDTOS;
    }

    private List<InspectionDTO> getAllByStaffMember(String id) throws FleetGenericException {
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

    private List<InspectionDTO> getAllWithDateBiggerThan(String date) throws FleetGenericException {
        try {
            LocalDateTime localDate = LocalDateTime.parse(date + "T00:00:00");
            return repository.selectInspectionWhereDateGreaterThan(localDate).stream().map(this::getInspectionDtoAndSetPlateNumberAndStaffId).collect(Collectors.toList());
        } catch (Exception e) {
            azureBlobLoggingServiceImpl.writeToLoggingFile("Failed to convert parse date " + date);
            throw new FleetItemNotFoundException();
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
            azureBlobLoggingServiceImpl.writeToLoggingFile("INSPECTION Filter could not be applied: " + filter + " " + option);
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

    // ------- EMAILING -------

    private void sendInspection(StaffMember staffMember, Inspection inspection) {
        try {
            emailSenderService.sendSimpleMessage(staffMember.getCorporateEmail(), "Your car was inspected",
                    emailComposerService.writeEmailToStaffAboutInspection(inspection, staffMember));
        } catch (Exception e) {
            azureBlobLoggingServiceImpl.writeToLoggingFile("Sending e-mail failed for inspection "
                    + inspection.getCarInspectionId());
        }
    }
}
