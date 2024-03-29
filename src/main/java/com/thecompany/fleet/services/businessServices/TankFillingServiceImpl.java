package com.thecompany.fleet.services.businessServices;

import com.thecompany.fleet.dto.TankFillingDTO;
import com.thecompany.fleet.dto.dtoUtils.DtoUtils;
import com.thecompany.fleet.errors.FleetGenericException;
import com.thecompany.fleet.errors.FleetItemNotFoundException;
import com.thecompany.fleet.models.enums.DiscrepancyType;
import com.thecompany.fleet.models.enums.filters.TankFillingFilter;
import com.thecompany.fleet.models.Car;
import com.thecompany.fleet.models.StaffMember;
import com.thecompany.fleet.models.TankFilling;
import com.thecompany.fleet.repositories.CarRepository;
import com.thecompany.fleet.repositories.StaffMemberRepository;
import com.thecompany.fleet.repositories.TankFillingRepository;
import com.thecompany.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import com.thecompany.fleet.services.utilServices.interfaces.EmailComposerService;
import com.thecompany.fleet.services.utilServices.interfaces.EmailSenderService;
import com.thecompany.fleet.services.businessServices.interfaces.TankFillingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TankFillingServiceImpl implements TankFillingService {
    private final TankFillingRepository repository;
    private final CarRepository carRepository;
    private final StaffMemberRepository staffMemberRepository;
    private final EmailComposerService emailComposerService;
    private final EmailSenderService emailSenderService;
    private final AzureBlobLoggingService azureBlobLoggingService;
    private final static Integer TOLERANCE_PERCENTAGE = 15;

    public TankFillingServiceImpl(TankFillingRepository repository, CarRepository carRepository, StaffMemberRepository staffMemberRepository, EmailComposerService emailComposerService, EmailSenderService emailSenderService, AzureBlobLoggingService azureBlobLoggingService) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.staffMemberRepository = staffMemberRepository;
        this.emailComposerService = emailComposerService;
        this.emailSenderService = emailSenderService;
        this.azureBlobLoggingService = azureBlobLoggingService;
    }

    @Override
    public TankFillingDTO read(Integer tankFillingId) {
        Optional<TankFilling> optionalTankFilling = repository.findById(tankFillingId);
        if (optionalTankFilling.isPresent()) {
            return getTankFillingDtoAndSetPlateNumber(optionalTankFilling.get());
        }
        azureBlobLoggingService.writeToLoggingFile("No fuel fill-up was found with id " + tankFillingId);
        throw new FleetItemNotFoundException();
    }

    @Override
    public List<TankFillingDTO> read(String filter, String option) {
        List<TankFillingDTO> tankFillingDTOS = new ArrayList<>();
        return filter(filter, option, tankFillingDTOS);
    }

    @Override
    @Transactional
    public TankFillingDTO create(TankFillingDTO tankFillingDTO) {
        TankFilling tankFilling = (TankFilling) new DtoUtils().convertToEntity(new TankFilling(), tankFillingDTO);
        Optional<Car> optionalCar = carRepository.findById(tankFillingDTO.getPlateNumber());
        if (optionalCar.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No car was found with plate number "
                    + tankFillingDTO.getPlateNumber() + "\nTANK FILLUP CREATION FAILED " + tankFillingDTO.getTankFillingId());
            throw new FleetGenericException();
        }
        Car car = optionalCar.get();
        tankFilling.setCar(car);
        tankFilling.setKmBefore(car.getKilometers());
        tankFilling.setDateTimeFilling(LocalDateTime.now());
        tankFilling.setConsumption(getConsumption(tankFilling));
        Double averageCarConsumptionWithTolerance = getAverageCarConsumptionWithTolerance(car);
        checkForDiscrepancies(tankFilling, averageCarConsumptionWithTolerance);
        boolean kilometersToCorrect = tankFilling.getDiscrepancy() && (
                tankFilling.getDiscrepancyType().equals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER)
                        || tankFilling.getDiscrepancyType().equals(DiscrepancyType.WRONG_FUEL));
        if (!kilometersToCorrect) {
            car.setKilometers(tankFilling.getKmAfter());
            carRepository.save(car);
        }
        repository.save(tankFilling);
        TankFillingDTO dto = (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
        dto.setPlateNumber(car.getPlateNumber());
        return dto;
    }

    @Override
    @Transactional
    public TankFillingDTO update(TankFillingDTO tankFillingDTO) {
        Optional<TankFilling> optionalTankFilling = repository.findById(tankFillingDTO.getTankFillingId());
        if (optionalTankFilling.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No fuel fill-up was found with id " + tankFillingDTO.getTankFillingId());
            throw new FleetGenericException();
        }
        TankFilling erroneousTankFilling = optionalTankFilling.get();
        TankFilling correctionTankFilling = cloneTankFilling(erroneousTankFilling);
        if (erroneousTankFilling.getDiscrepancyType().equals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER)
                || erroneousTankFilling.getDiscrepancyType().equals(DiscrepancyType.WRONG_FUEL)) {
            Car car = carRepository.findById(correctionTankFilling.getCar().getPlateNumber()).orElseThrow(FleetItemNotFoundException::new);
            correctionTankFilling.setKmAfter(tankFillingDTO.getKmAfter());
            correctionTankFilling.setConsumption(getConsumption(correctionTankFilling));
            Double averageCarConsumptionWithTolerance = getAverageCarConsumptionWithTolerance(car);
            checkForDiscrepancies(correctionTankFilling, averageCarConsumptionWithTolerance);
            car.setKilometers(correctionTankFilling.getKmAfter());
            carRepository.save(car);
            correctionTankFilling.setCorrectionForId(erroneousTankFilling.getTankFillingId());
            repository.save(correctionTankFilling);
            erroneousTankFilling.setCorrectedById(correctionTankFilling.getTankFillingId());
            repository.save(erroneousTankFilling);
            return (TankFillingDTO) new DtoUtils().convertToDto(correctionTankFilling, new TankFillingDTO());
        } else {
            azureBlobLoggingService.writeToLoggingFile("Trying to correct tank filling that cannot be correctd " + tankFillingDTO.getTankFillingId());
            throw new FleetGenericException("Cannot correct tank filling");
        }
    }

    /* PRIVATE METHODS */

    // ------- CALCULATION UTILS -------

    /*
     * returns the actual consumption in liters per 100 kilometers
     */
    private Double getConsumption(TankFilling tankFilling) {
        double consumption = (tankFilling.getLiters() * 100) / (tankFilling.getKmAfter() - tankFilling.getKmBefore());
        return (double) (Math.round(consumption * 100) / 100);
    }

    /*
     * returns the actual consumption in liters per 100 kilometers taking into account the tolerance margin
     */
    private Double getAverageCarConsumptionWithTolerance(Car car) {
        return car.getAverageConsumption() + (car.getAverageConsumption() / 100 * TOLERANCE_PERCENTAGE);
    }

    /**
     * Search for discrepancies based on received data and set discrepancy type
     * Increment number of discrepancies of staff member if discrepancy
     * Send an email to fleet manager if discrepancy
     *
     * @param tankFilling
     * @param consumptionWithTolerance
     */
    private void checkForDiscrepancies(TankFilling tankFilling, Double consumptionWithTolerance) {
        Car car = tankFilling.getCar();
        if (car.getFuelType().getCode() != tankFilling.getFuelType().getCode()) { // use a code so it doesn't generate discrepancy for hybrids
            tankFilling.setConsumption(0.0);
            tankFilling.setDiscrepancyType(DiscrepancyType.WRONG_FUEL);
        } else if (tankFilling.getKmBefore() >= tankFilling.getKmAfter()) {
            tankFilling.setConsumption(0.0);
            tankFilling.setDiscrepancyType(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER);
        } else if (tankFilling.getConsumption() > consumptionWithTolerance) {
            tankFilling.setDiscrepancyType(DiscrepancyType.QUANTITY_TOO_HIGH);
        } else {
            tankFilling.setDiscrepancy(false);
            return;
        }
        // executed if the app doesn't enter the "else" instruction, thus in case of discrepancy:
        tankFilling.setDiscrepancy(true);
        StaffMember staffMember = car.getStaffMember();
        if (staffMember != null) {
            staffMember.setNumberDiscrepancies(staffMember.getNumberDiscrepancies() == null ?
                    1 : staffMember.getNumberDiscrepancies() + 1);
            staffMemberRepository.save(staffMember);
        } else {
            azureBlobLoggingService.writeToLoggingFile("Tank filling with ID "
                    + tankFilling.getTankFillingId() + " has discrepancy but staffMember could not be retrieved");
            // non blocking, non transactional -> don't throw exception
        }
        sendEmail(tankFilling);
    }

    // ------- FILTERING -------

    private List<TankFillingDTO> filter(String filter, String option, List<TankFillingDTO> tankFillingDTOS) {
        try {
            TankFillingFilter tankFillingFilter = TankFillingFilter.valueOf(filter);
            switch (tankFillingFilter) {
                case ALL:
                    return getAllTankFillings(tankFillingDTOS);
                case WITH_DISCREPANCY:
                    return getAllWithDiscrepancy();
                case DATE_ABOVE:
                    return getAllWithDateBiggerThan(option);
                case WITH_DISCREPANCY_NOT_CORRECTED:
                    return getAllWithDiscrepancyAndNotCorrected();
                default: throw new FleetGenericException();
            }
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("TANK FILLING Filter could not be applied: " + filter + option);
            throw new FleetGenericException();
        }
    }

    private List<TankFillingDTO> getAllTankFillings(List<TankFillingDTO> tankFillingsDTOs) {
        for (TankFilling tankFilling : repository.findAll()) {
            tankFillingsDTOs.add(getTankFillingDtoAndSetPlateNumber(tankFilling));
        }
        return tankFillingsDTOs;
    }

    private List<TankFillingDTO> getAllWithDateBiggerThan(String date) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date + "T00:00:00");
            return repository.selectFillupWhereDateGreaterThan(localDateTime).stream().map(this::getTankFillingDtoAndSetPlateNumber).collect(Collectors.toList());
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("Failed to convert parse date " + date);
            throw new FleetGenericException();
        }
    }

    private List<TankFillingDTO> getAllWithDiscrepancyAndNotCorrected() {
        return repository.selectFillupWhereWhereDiscrepancyIsTrueAndCorrectedByIsNull().stream().map(this::getTankFillingDtoAndSetPlateNumber).collect(Collectors.toList());
    }

    private List<TankFillingDTO> getAllWithDiscrepancy() {
        return repository.selectTankFillingWhereDiscrepancyIsTrue().stream().map(this::getTankFillingDtoAndSetPlateNumber).collect(Collectors.toList());
    }

    // ------- DATA TRANSFORMATION -------

    /**
     * Transform TankFilling into TankFillingDTO and set PlateNumber
     *
     * @param tankFilling The Tank filling to transform
     * @return The Tank filling DTO with Plate Number set
     */
    private TankFillingDTO getTankFillingDtoAndSetPlateNumber(TankFilling tankFilling) {
        TankFillingDTO tfDTO = (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
        tfDTO.setPlateNumber(tankFilling.getCar().getPlateNumber());
        return tfDTO;
    }

    private TankFilling cloneTankFilling(TankFilling tankFilling) {
        TankFilling clone = new TankFilling();
        clone.setCar(tankFilling.getCar());
        clone.setKmBefore(tankFilling.getKmBefore());
        clone.setLiters(tankFilling.getLiters());
        clone.setFuelType(tankFilling.getCar().getFuelType());
        clone.setDateTimeFilling(tankFilling.getDateTimeFilling());
        return clone;
    }

    // ------- EMAILING -------

    private void sendEmail(TankFilling tankFilling) {
        try {
            emailSenderService.sendSimpleMessage("florence.mary@iramps.email", "New discrepancy",
                    emailComposerService.writeEmailToFleetManagerAboutDiscrepancy(tankFilling));
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("Sending e-mail failed for erroneous tank fillup "
                    + tankFilling.getTankFillingId());
            throw new FleetGenericException("Could not send e-mail");
        }
    }
}
