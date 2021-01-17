package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.interfaces.StaffMemberService;
import com.soprasteria.fleet.services.utilServices.ExcelStaffDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
    private final StaffMemberRepository repository;
    private final CarRepository carRepository;
    private final ExcelStaffDataService excelStaffDataService;

    public StaffMemberServiceImpl(StaffMemberRepository repository, CarRepository carRepository, ExcelStaffDataService excelStaffDataService) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.excelStaffDataService = excelStaffDataService;
    }

    @Override
    public StaffMemberDTO read(Integer staffMemberId) {
        StaffMember staffMember = repository.findById(staffMemberId).get();
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<StaffMemberDTO> readAll() {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        for (StaffMember staffMember : repository.findAll()) {
            staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
        }
        return staffMemberDTOS;
    }

    @Override
    public List<StaffMemberDTO> readAllWithCar() {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        for (StaffMember staffMember : repository.findAll()) {
            if (staffMember.getHasCar()) {
                staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
            }
        }
        return staffMemberDTOS;
    }

    @Override
    public List<StaffMemberDTO> readAllWithoutCar() {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        for (StaffMember staffMember : repository.findAll()) {
            if (!staffMember.getHasCar()) {
                staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
            }
        }
        return staffMemberDTOS;
    }

    @Override
    public StaffMemberDTO update(StaffMemberDTO staffMemberDTO) {
        StaffMember staffMember = repository.findById(staffMemberDTO.getStaffMemberId()).get();
        staffMember.setHasCar(staffMemberDTO.getHasCar());
        excelStaffDataService.updateHasCar(staffMember.getCorporateEmail(), staffMember.getHasCar());
        repository.save(staffMember);
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<CarDTO> getCarsOfStaffMember(Integer staffMemberId) {
        List<CarDTO> carDTOS = new ArrayList<>();
        StaffMember staffMember = repository.findById(staffMemberId).get();
        for (Car car : staffMember.getCars()) {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        }
        return carDTOS;
    }

    @Override
    public StaffMemberDTO setCarOfStaffMember(Integer staffMemberId, String carPlate) {
        StaffMember staffMember = repository.findById(staffMemberId).get();
        staffMember.getCars().stream()
                .filter(Car::getOngoing)
                .findFirst()
                .ifPresent(car -> {
                    car.setOngoing(false);
                    carRepository.save(car);
                });
        Car currentCar = carRepository.findById(carPlate).get();
        currentCar.setStaffMember(staffMember);
        currentCar.setOngoing(true);
        carRepository.save(currentCar);
        return (StaffMemberDTO) new DtoUtils().convertToDto(currentCar, new CarDTO());
    }

    @Override
    public CarDTO getCurrentCarOfStaffMember(Integer staffMemberId) {
        StaffMember staffMember = repository.findById(staffMemberId).get();
        List<Car> cars = staffMember.getCars().stream()
                .filter(Car::getOngoing)
                .collect(Collectors.toList());
        if (cars.size() == 1) {
            return (CarDTO) new DtoUtils().convertToDto(cars.get(0), new CarDTO());
        } else if (cars.size() > 1) {
            // TODO logger
            // ongoing cars should never be > 1
            return null;
        } else {
            // staff member has no current car
            return null;
        }
    }
}
