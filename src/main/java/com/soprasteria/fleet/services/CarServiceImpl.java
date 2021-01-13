package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.services.interfaces.CarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarDTO read(String plateNumber) {
        Car car = repository.findById(plateNumber).get();
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }

    @Override
    public List<CarDTO> readAll() {
        List<CarDTO> carDTOS = new ArrayList<>();
        for(Car car: repository.findAll()) {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        }
        return carDTOS;
    }

    @Override
    public List<CarDTO> readAllActive() {
        List<CarDTO> carDTOS = new ArrayList<>();
        for(Car car: repository.findAll()) {
            if (!car.isArchived()) {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            }
        }
        return carDTOS;
    }

    @Override
    public List<CarDTO> readAllArchived() {
        List<CarDTO> carDTOS = new ArrayList<>();
        for(Car car: repository.findAll()) {
            if (car.isArchived()) {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            }
        }
        return carDTOS;
    }

    @Override
    public CarDTO create(CarDTO carDTO) {
        Car car = (Car) new DtoUtils().convertToEntity(new Car(), carDTO);
        repository.save(car);
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }

    @Override
    public String delete(String plateNumber) {
        Car car = repository.findById(plateNumber).get();
        car.setArchived(true);
        repository.save(car);
        return "Car " + plateNumber + " is archived";
    }

    @Override
    public CarDTO update(CarDTO carDTO) {
        Car car = repository.findById(carDTO.getPlateNumber()).get();
        if (carDTO.getBrand() != null) {
            car.setBrand(carDTO.getBrand());
        }
        if (carDTO.getModel() != null) {
            car.setModel(carDTO.getModel());
        }
        if (carDTO.getChassisNumber() != null) {
            car.setChassisNumber(carDTO.getChassisNumber());
        }
        if (carDTO.getFuel() != null) {
            car.setFuelType(carDTO.getFuel());
        }
        if (carDTO.getAverageConsumption() != null) {
            car.setAverageConsumption(carDTO.getAverageConsumption());
        }
        if (carDTO.getPlateNumber() != null) {
            car.setPlateNumber(carDTO.getPlateNumber());
        }
        if (carDTO.getRegistrationDate() != null) {
            car.setRegistrationDate(carDTO.getRegistrationDate());
        }
        repository.save(car);
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }
}
