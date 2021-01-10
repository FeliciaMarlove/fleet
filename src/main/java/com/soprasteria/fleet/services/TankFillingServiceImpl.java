package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.DiscrepancyLevel;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.TankFillingRepository;
import com.soprasteria.fleet.services.interfaces.TankFillingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TankFillingServiceImpl implements TankFillingService {
    private final TankFillingRepository repository;
    private final CarRepository carRepository;

    public TankFillingServiceImpl(TankFillingRepository repository, CarRepository carRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
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
    public TankFillingDTO create(TankFillingDTO tankFillingDTO) {
        Optional<Car> car = carRepository.findById(tankFillingDTO.getPlateNumber());
        TankFilling tankFilling = (TankFilling) new DtoUtils().convertToEntity(new TankFilling(), tankFillingDTO);
        tankFilling.setCar(car.get());
        tankFilling.setDateTimeFilling(LocalDateTime.now());
        checkForDiscrepancies(tankFilling);
        repository.save(tankFilling);
        return (TankFillingDTO) new DtoUtils().convertToDto(tankFilling, new TankFillingDTO());
    }

    @Override
    public TankFillingDTO update(TankFillingDTO tankFillingDTO) {
        // TODO affecter discrepancy (bool) + éventuellement changer level et +1 compteur du staff member dans les cas où une validation manuelle est requise
        return null;
    }

    private void checkForDiscrepancies(TankFilling tankFilling) {
        if (tankFilling.getCar().getFuelType() != tankFilling.getFuelType()) {
            tankFilling.setDiscrepancy(true);
            tankFilling.setDiscrepancyType(DiscrepancyType.WRONG_FUEL);
            tankFilling.setDiscrepancyLevel(DiscrepancyLevel.SEVERE);
            // TODO +1 compteur staff member
        }
        // calculs pour carbu et division acceptable/assumed/severe
        // TODO logique discrepancy ici (affecter booléen, Level et Type)
    }
}
