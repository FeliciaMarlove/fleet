package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.CarEmployeeLinking;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.repositories.CarEmployeeRepository;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.repositories.TankFillingRepository;
import com.soprasteria.fleet.services.interfaces.TankFillingService;
import com.soprasteria.fleet.utils.DiscrepancyMailComposer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TankFillingServiceImpl implements TankFillingService {
    private final TankFillingRepository repository;
    private final CarRepository carRepository;
    private final CarEmployeeRepository carEmployeeRepository;
    private final StaffMemberRepository staffMemberRepository;
    private final Integer tolerancePercentage = 15;

    public TankFillingServiceImpl(TankFillingRepository repository, CarRepository carRepository, CarEmployeeRepository carEmployeeRepository, StaffMemberRepository staffMemberRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.carEmployeeRepository = carEmployeeRepository;
        this.staffMemberRepository = staffMemberRepository;
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
        List<TankFillingDTO> tankFillings = new ArrayList<>();
        for (TankFilling tankFilling : repository.findAll()) {
            if (tankFilling.getDiscrepancy()) {
                tankFillings.add((TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO()));
            }
        }
        return tankFillings;
    }

    @Override
    public TankFillingDTO create(TankFillingDTO tankFillingDTO) {
        Car car = carRepository.findById(tankFillingDTO.getPlateNumber()).get();
        TankFilling tankFilling = (TankFilling) new DtoUtils().convertToEntity(new TankFilling(), tankFillingDTO);
        tankFilling.setCar(car);
        tankFilling.setKmBefore(car.getKilometers());
        car.setKilometers(tankFilling.getKmAfter());
        tankFilling.setDateTimeFilling(LocalDateTime.now());
        Double consumption = (tankFilling.getLiters() * 100) / (tankFilling.getKmAfter() - tankFilling.getKmBefore());
        Double consumptionWithTolerance = consumption + (consumption / 100 * tolerancePercentage);
        tankFilling.setConsumption(consumptionWithTolerance);
        checkForDiscrepancies(tankFilling);
        repository.save(tankFilling);
        carRepository.save(car);
        return (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
    }

    private void checkForDiscrepancies(TankFilling tankFilling) {
        Car car = tankFilling.getCar();
        Optional<CarEmployeeLinking> carEmployeeLinking = carEmployeeRepository.getCarEmployeeLinkingByCarAndIsOngoing(car, true);
        if (carEmployeeLinking.isPresent()) {
            System.out.println(carEmployeeLinking);
            StaffMember staffMember = carEmployeeLinking.get().getStaffMember();
            if (car.getFuelType() != tankFilling.getFuelType()) {
                tankFilling.setDiscrepancyType(DiscrepancyType.WRONG_FUEL);
            } else if (tankFilling.getKmBefore() > tankFilling.getKmAfter()) {
                tankFilling.setDiscrepancyType(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER);
            } else if (tankFilling.getConsumption() > car.getAverageConsumption()) {
                tankFilling.setDiscrepancyType(DiscrepancyType.QUANTITY_TOO_HIGH);
            } else {
                return;
            }
            // executed if the app doesn't enter the "else" block (then for each case where a discrepancy occurs)
            tankFilling.setDiscrepancy(true);
            staffMember.setNumberActualDiscrepancies(staffMember.getNumberActualDiscrepancies() + 1);
            staffMemberRepository.save(staffMember);

            DiscrepancyMailComposer discrepancyMailComposer = new DiscrepancyMailComposer(tankFilling, staffMember);
            discrepancyMailComposer.writeEmail();
            // TODO créer un service email qui utilise le mailComposer (pas stocké)
        }

    }
}
