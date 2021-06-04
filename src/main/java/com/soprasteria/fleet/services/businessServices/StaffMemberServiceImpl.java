package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.filters.StaffFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.utilServices.interfaces.ExcelStaffDataService;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
    private final StaffMemberRepository repository;
    private final CarRepository carRepository;

    public StaffMemberServiceImpl(StaffMemberRepository repository, CarRepository carRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
    }

    @Override
    public StaffMemberDTO read(Integer staffMemberId) {
        StaffMember staffMember = repository.findById(staffMemberId).get();
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<StaffMemberDTO> read(String filter, String option) {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        return filter(filter, option, staffMemberDTOS);
    }

    @Override
    public StaffMemberDTO update(StaffMemberDTO staffMemberDTO) {
        StaffMember staffMember = repository.findById(staffMemberDTO.getStaffMemberId()).get();
        staffMember.setHasCar(staffMemberDTO.getHasCar());
        repository.save(staffMember);
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<CarDTO> getCarsOfStaffMember(Integer staffMemberId) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.selectCarWhereStaffIdIs(staffMemberId);
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    @Override
    public CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate) {
        StaffMember staffMember = repository.findById(staffMemberId).orElseThrow();

        Optional<Car> optionalCar = carRepository.selectCarWhereStaffIdIsAndOngoingTrue(staffMemberId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.orElseThrow();
            car.setOngoing(false);
            carRepository.save(car);
        }

        if (carPlate != null) {
            Car currentCar = carRepository.findById(carPlate).orElseThrow();
            currentCar.setStaffMember(staffMember);
            currentCar.setOngoing(true);
            carRepository.save(currentCar);
            if (!staffMember.getHasCar()) {
                staffMember.setHasCar(true);
                repository.save(staffMember);
            }
            return (CarDTO) new DtoUtils().convertToDto(currentCar, new CarDTO());
        } else {
            return null;
        }
    }

    @Override
    public CarDTO getCurrentCarOfStaffMember(Integer staffMemberId) {
        Optional<Car> optionalCar = carRepository.selectCarWhereStaffIdIsAndOngoingTrue(staffMemberId);
        return optionalCar.map(car -> (CarDTO) new DtoUtils().convertToDto(car, new CarDTO())).orElse(null);
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<StaffMemberDTO> filter(String filter, String option, List<StaffMemberDTO> staffMemberDTOS) {
        try {
            StaffFilter staffFilter = StaffFilter.valueOf(filter);
            switch (staffFilter) {
                case ALL: default: return getAllStaff(staffMemberDTOS);
                case WITH: return getAllWithCar(staffMemberDTOS);
                case WITHOUT: return getAllWithoutCar(staffMemberDTOS);
            }
        } catch (Exception e) {
            System.out.println(e);
            return getAllStaff(staffMemberDTOS);
        }
    }

    private List<StaffMemberDTO> getAllStaff(List<StaffMemberDTO> staffMemberDTOS) {
        for (StaffMember staffMember : repository.findAll()) {
            staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
        }
        return staffMemberDTOS;
    }

    private List<StaffMemberDTO> getAllWithCar(List<StaffMemberDTO> staffMemberDTOS) {
        for (StaffMember staffMember : repository.selectFromStaffWhereCarTrue()) {
                staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
        }
        return staffMemberDTOS;
    }

    private List<StaffMemberDTO> getAllWithoutCar(List<StaffMemberDTO> staffMemberDTOS) {
        for (StaffMember staffMember : repository.selectFromStaffWhereCarFalse()) {
                staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
        }
        return staffMemberDTOS;
    }
}
