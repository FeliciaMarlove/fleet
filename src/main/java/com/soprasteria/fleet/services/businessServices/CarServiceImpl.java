package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.enums.filters.CarFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
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
        return getCarDtoAndSetMemberId(car);
    }

    @Override
    public List<CarDTO> read(String filter, String option) {
        List<CarDTO> carDTOS = new ArrayList<>();
        return filter(filter, option, carDTOS);
    }

    @Override
    public CarDTO create(CarDTO carDTO) {
        Car car = (Car) new DtoUtils().convertToEntity(new Car(), carDTO);
        repository.save(car);
        if (carDTO.getStaffMemberId() != null) {
            setStaffMember(car, carDTO);
        }
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
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
        if (carDTO.getFuelType() != null) {
            car.setFuelType(carDTO.getFuelType());
        }
        if (carDTO.getAverageConsumption() != null) {
            car.setAverageConsumption(carDTO.getAverageConsumption());
        }
        if (carDTO.getPlateNumber() != null) {
            car.setPlateNumber(carDTO.getPlateNumber());
        }
        if (carDTO.getFreeText() != null) {
            car.setFreeText(carDTO.getFreeText());
        }
        if (carDTO.getStaffMemberId() != null) {
            setStaffMember(car, carDTO);
        }
        if (carDTO.getEndDate() != null) {
            car.setEndDate(carDTO.getEndDate());
        }
        repository.save(car);
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }


    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<CarDTO> getAllCars(List<CarDTO> carDTOS) {
        for(Car car: repository.findAll()) {
            carDTOS.add(getCarDtoAndSetMemberId(car));
        }
        return carDTOS;
    }

    private List<CarDTO> getAllActive(List<CarDTO> carDTOS) {
        List<Car> cars = repository.selectFromCarWhereOngoing();
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    private List<CarDTO> getAllArchived(List<CarDTO> carDTOS) {
        List<Car> cars = repository.selectFromCarWhereNotOngoing();
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    private List<CarDTO> getByBrand(List<CarDTO> carDTOS, String brandName) {
        Brand brand = Brand.valueOf(brandName);
        List<Car> cars = repository.selectFromCarWhereBrandIs(brand.ordinal());
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    private List<CarDTO> getByFuel(List<CarDTO> carDTOS, String fuel) {
        FuelType fuelType = FuelType.valueOf(fuel);
        List<Car> cars = repository.selectFromCarWhereFuelIs(fuelType.ordinal());
        cars.forEach( car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    private List<CarDTO> filter(String filter, String option, List<CarDTO> carDTOS) {
        try {
            CarFilter carFilter = CarFilter.valueOf(filter);
            switch (carFilter) {
                case ALL: default: return getAllCars(carDTOS);
                case ACTIVE: return getAllActive(carDTOS);
                case ARCHIVED: return getAllArchived(carDTOS);
                case FUEL: return getByFuel(carDTOS, option);
                case BRAND: return getByBrand(carDTOS, option);
            }
        } catch (Exception e) {
            System.out.println(e);
            return getAllCars(carDTOS);
        }
    }

    // ------- DATA TRANSFORMATION -------

    private void setStaffMember(Car car, CarDTO carDTO) {
        StaffMember staffMember = staffMemberRepository.findById(carDTO.getStaffMemberId()).get();
        car.setStaffMember(staffMember);
        staffMemberService.setCarOfStaffMember(staffMember.getStaffMemberId(), car.getPlateNumber());
        staffMemberRepository.save(staffMember);
        repository.save(car);
    }

    /**
     * Transform Car into CarDTO and set MemberID
     * @param car The car to transform
     * @return The CarDTO with MemberID set
     */
    private CarDTO getCarDtoAndSetMemberId(Car car) {
        CarDTO carDTO = (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
        if (car.getStaffMember() != null) {
            carDTO.setStaffMemberId(car.getStaffMember().getStaffMemberId());
        }
        return carDTO;
    }
}
