package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.repositories.TankFillingRepository;
import com.soprasteria.fleet.services.interfaces.EmailComposerService;
import com.soprasteria.fleet.services.interfaces.EmailSenderService;
import com.soprasteria.fleet.services.interfaces.TankFillingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TankFillingServiceImpl implements TankFillingService {
    private final TankFillingRepository repository;
    private final CarRepository carRepository;
    private final StaffMemberRepository staffMemberRepository;
    private final EmailComposerService emailComposerService;
    private final EmailSenderService emailSenderService;
    private final Integer TOLERANCE_PERCENTAGE = 15;

    public TankFillingServiceImpl(TankFillingRepository repository, CarRepository carRepository, StaffMemberRepository staffMemberRepository, EmailComposerService emailComposerService, EmailSenderService emailSenderService) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.staffMemberRepository = staffMemberRepository;
        this.emailComposerService = emailComposerService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public TankFillingDTO read(Integer tankFillingId) {
        TankFilling tankFilling = repository.findById(tankFillingId).get();
        return (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
    }

    @Override
    public List<TankFillingDTO> readAll() {
        List<TankFillingDTO> tankFillings = new ArrayList<>();
        for (TankFilling tankFilling : repository.findAll()) {
            tankFillings.add((TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO()));
        }
        return tankFillings;
    }

    @Override
    public List<TankFillingDTO> readAllDiscrepancy() {
        List<TankFillingDTO> tankFillingDTOS = new ArrayList<>();
        List<TankFilling> fillings = repository.selectTankFillingWhereDiscrepancyIsTrue();
        fillings.forEach( filling -> {
            tankFillingDTOS.add((TankFillingDTO) new DtoUtils().convertToDto(filling, new TankFillingDTO()));
        });
        return tankFillingDTOS;
    }

    @Override
    public TankFillingDTO create(TankFillingDTO tankFillingDTO) {
        TankFilling tankFilling = (TankFilling) new DtoUtils().convertToEntity(new TankFilling(), tankFillingDTO);
        Car car = carRepository.findById(tankFillingDTO.getPlateNumber()).get();
        tankFilling.setCar(car);
        tankFilling.setKmBefore(car.getKilometers());
        car.setKilometers(tankFilling.getKmAfter());
        tankFilling.setDateTimeFilling(LocalDateTime.now());
        tankFilling.setConsumption(getConsumption(tankFilling));
        Double averageCarConsumptionWithTolerance = getAverageCarConsumptionWithTolerance(car);
        checkForDiscrepancies(tankFilling, averageCarConsumptionWithTolerance);
        repository.save(tankFilling);
        carRepository.save(car);
        return (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
    }

    @Override
    public TankFillingDTO update(TankFillingDTO tankFillingDTO) {
        TankFilling erroneousTankFilling = repository.findById(tankFillingDTO.getTankFillingId()).get();
        TankFilling correctionTankFilling = cloneTankFilling(erroneousTankFilling);
        if (erroneousTankFilling.getDiscrepancyType().equals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER)
        || erroneousTankFilling.getDiscrepancyType().equals(DiscrepancyType.WRONG_FUEL)) {
            Car car = carRepository.findById(correctionTankFilling.getCar().getPlateNumber()).get();
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
            return null;
        }
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

    /*
     * returns the actual consumption in liters per 100 kilometers
     */
    private Double getConsumption(TankFilling tankFilling) {
        double consumption = (tankFilling.getLiters() * 100) / (tankFilling.getKmAfter() - tankFilling.getKmBefore());
        return (double)(Math.round(consumption * 100) / 100);
    }

    /*
     * returns the actual consumption in liters per 100 kilometers taking into account the tolerance margin
     */
    private Double getAverageCarConsumptionWithTolerance(Car car) {
        return car.getAverageConsumption() + (car.getAverageConsumption() / 100 * TOLERANCE_PERCENTAGE);
    }

    private void checkForDiscrepancies(TankFilling tankFilling, Double consumptionWithTolerance) {
        Car car = tankFilling.getCar();
        StaffMember staffMember = car.getStaffMember();
        if (car.getFuelType() != tankFilling.getFuelType()) {
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
        staffMember.setNumberActualDiscrepancies(staffMember.getNumberActualDiscrepancies() + 1);
        staffMemberRepository.save(staffMember);
        sendEmail(tankFilling);
    }

    private void sendEmail(TankFilling tankFilling) {
        emailSenderService.sendSimpleMessage("fleet.tfe.2021@gmail.com", "New discrepancy", emailComposerService.writeEmailToFleetManagerAboutDiscrepancy(tankFilling));
    }
}
