package com.thecompany.fleet.services.businessServices;

import com.thecompany.fleet.dto.CarDTO;
import com.thecompany.fleet.dto.StaffMemberDTO;
import com.thecompany.fleet.dto.dtoUtils.DtoUtils;
import com.thecompany.fleet.errors.FleetGenericException;
import com.thecompany.fleet.errors.FleetItemNotFoundException;
import com.thecompany.fleet.models.enums.filters.StaffFilter;
import com.thecompany.fleet.models.Car;
import com.thecompany.fleet.models.StaffMember;
import com.thecompany.fleet.repositories.CarRepository;
import com.thecompany.fleet.repositories.StaffMemberRepository;
import com.thecompany.fleet.services.businessServices.interfaces.StaffMemberService;
import com.thecompany.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
    private final StaffMemberRepository repository;
    private final CarRepository carRepository;
    private final AzureBlobLoggingService azureBlobLoggingService;

    public StaffMemberServiceImpl(StaffMemberRepository repository, CarRepository carRepository, AzureBlobLoggingService azureBlobLoggingService) {
        this.repository = repository;
        this.carRepository = carRepository;
        this.azureBlobLoggingService = azureBlobLoggingService;
    }

    @Override
    public StaffMemberDTO read(Integer staffMemberId) {
        StaffMember staffMember = repository.findById(staffMemberId).orElseThrow(FleetItemNotFoundException::new);
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<StaffMemberDTO> read(String filter, String option) {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        return filter(filter, option, staffMemberDTOS);
    }

    @Override
    public StaffMemberDTO update(StaffMemberDTO staffMemberDTO) {
        Optional<StaffMember> optionalStaffMember = repository.findById(staffMemberDTO.getStaffMemberId());
        if (optionalStaffMember.isPresent()) {
            StaffMember staffMember = optionalStaffMember.get();
            staffMember.setHasCar(staffMemberDTO.getHasCar());
            repository.save(staffMember);
            return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
        }
        azureBlobLoggingService.writeToLoggingFile("Could not find staff member with id " + staffMemberDTO.getStaffMemberId());
        throw new FleetItemNotFoundException();
    }

    @Override
    public List<CarDTO> getCarsOfStaffMember(Integer staffMemberId) {
        List<CarDTO> carDTOS = new ArrayList<>();
        List<Car> cars = carRepository.selectFromCarWhereStaffIdIs(staffMemberId);
        cars.forEach(car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    @Override
    @Transactional
    public CarDTO setCarOfStaffMember(Integer staffMemberId, String carPlate) {
        Optional<StaffMember> optionalStaffMember = repository.findById(staffMemberId);
        if (optionalStaffMember.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No staff member was found with id " + staffMemberId);
            throw new FleetItemNotFoundException();
        }

        // Optional is empty if staff member has no car at the moment, not an error case
        Optional<Car> optionalCar = carRepository.selectCarWhereStaffIdIsAndOngoingTrue(staffMemberId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setOngoing(false);
            carRepository.save(car);
        }

        StaffMember staffMember = optionalStaffMember.get();

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
            throw new FleetItemNotFoundException();
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
                case ALL:
                    return getAllStaff(staffMemberDTOS);
                case WITH:
                    return getAllWithCar(staffMemberDTOS);
                case WITHOUT:
                    return getAllWithoutCar(staffMemberDTOS);
                default: throw new FleetGenericException();
            }
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("STAFF MEMBER Filter could not be applied: " + filter + option);
            throw new FleetGenericException();
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
