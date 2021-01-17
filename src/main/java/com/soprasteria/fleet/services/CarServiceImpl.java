package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.interfaces.CarService;
import com.soprasteria.fleet.services.interfaces.StaffMemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final StaffMemberRepository staffMemberRepository;
    private final StaffMemberService staffMemberService;

    public CarServiceImpl(CarRepository repository, StaffMemberRepository staffMemberRepository, StaffMemberService staffMemberService) {
        this.repository = repository;
        this.staffMemberRepository = staffMemberRepository;
        this.staffMemberService = staffMemberService;
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
            if (car.getOngoing()) {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            }
        }
        return carDTOS;
    }

    @Override
    public List<CarDTO> readAllArchived() {
        List<CarDTO> carDTOS = new ArrayList<>();
        for(Car car: repository.findAll()) {
            if (!car.getOngoing()) {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            }
        }
        return carDTOS;
    }

    @Override
    public CarDTO create(CarDTO carDTO) {
        Car car = (Car) new DtoUtils().convertToEntity(new Car(), carDTO);
        if (carDTO.getStaffMemberId() != null) {
            StaffMember staffMember = staffMemberRepository.findById(carDTO.getStaffMemberId()).get();
            car.setStaffMember(staffMember);
            staffMemberService.setCarOfStaffMember(staffMember.getStaffMemberId(), car.getPlateNumber());
            staffMemberRepository.save(staffMember);
        }
        repository.save(car);
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }

    @Override
    public String delete(String plateNumber) {
        Car car = repository.findById(plateNumber).get();
        car.setOngoing(false);
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
        if (carDTO.getFuelType() != null) {
            car.setFuelType(carDTO.getFuelType());
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
        if (carDTO.getFreeText() != null) {
            car.setFreeText(car.getFreeText());
        }
        if (carDTO.getStaffMemberId() != null) {
            StaffMember staffMember = staffMemberRepository.findById(carDTO.getStaffMemberId()).get();
            car.setStaffMember(staffMember);
            staffMemberService.setCarOfStaffMember(staffMember.getStaffMemberId(), car.getPlateNumber());
            staffMemberRepository.save(staffMember);
        }
        repository.save(car);
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }
}