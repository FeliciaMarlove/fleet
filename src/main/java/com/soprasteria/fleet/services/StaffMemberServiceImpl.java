package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.interfaces.ExcelStaffDataService;
import com.soprasteria.fleet.services.interfaces.StaffMemberService;
import com.soprasteria.fleet.services.utilServices.ExcelStaffDataServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<Car> cars = repository.selectCarWhereStaffIdIs(staffMemberId);
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    @Override
    public CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate) {
        StaffMember staffMember = repository.findById(staffMemberId).get();

        Optional<Car> optionalCar = repository.selectCarWhereStaffIdIsAndOngoingTrue(staffMemberId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setOngoing(false);
            carRepository.save(car);
        }
        Car currentCar = carRepository.findById(carPlate).get();
        currentCar.setStaffMember(staffMember);
        currentCar.setOngoing(true);
        carRepository.save(currentCar);
        return (CarDTO) new DtoUtils().convertToDto(currentCar, new CarDTO());
    }

    @Override
    public CarDTO getCurrentCarOfStaffMember(Integer staffMemberId) {
        Optional<Car> optionalCar = repository.selectCarWhereStaffIdIsAndOngoingTrue(staffMemberId);
        if (optionalCar.isPresent()) {
            return (CarDTO) new DtoUtils().convertToDto(optionalCar.get(), new CarDTO());
        }
        return null;
    }
}
