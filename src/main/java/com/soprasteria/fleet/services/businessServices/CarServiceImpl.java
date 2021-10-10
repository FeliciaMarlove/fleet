package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.models.enums.Brand;
import com.soprasteria.fleet.models.enums.FuelType;
import com.soprasteria.fleet.models.enums.filters.CarFilter;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.InspectionRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
import com.soprasteria.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class CarServiceImpl implements CarService {
    private final AzureBlobLoggingService azureBlobLoggingService;
    private final CarRepository repository;
    private final StaffMemberRepository staffMemberRepository;
    private final StaffMemberService staffMemberService;
    private final InspectionRepository inspectionRepository;

    public CarServiceImpl(AzureBlobLoggingService azureBlobLoggingService, CarRepository repository, StaffMemberRepository staffMemberRepository, StaffMemberService staffMemberService, InspectionRepository inspectionRepository) {
        this.azureBlobLoggingService = azureBlobLoggingService;
        this.repository = repository;
        this.staffMemberRepository = staffMemberRepository;
        this.staffMemberService = staffMemberService;
        this.inspectionRepository = inspectionRepository;
    }

    @Override
    public CarDTO read(String plateNumber) {
        Optional<Car> car = repository.findById(plateNumber);
        if (car.isPresent()) return getCarDtoAndSetMemberId(car.get());
        else azureBlobLoggingService.writeToLoggingFile("No car found with plate " + plateNumber);
        throw new FleetItemNotFoundException();
    }

    @Override
    public List<CarDTO> read(String filter, String option) {
        List<CarDTO> carDTOS = new ArrayList<>();
        return filter(filter, option, carDTOS);
    }

    @Override
    @Transactional
    public CarDTO create(CarDTO carDTO) {
        // manual check because if not using auto-generated ids, repository.save does update on existing ID
        if (repository.findById(carDTO.getPlateNumber()).isPresent()) {
            azureBlobLoggingService.writeToLoggingFile("Can't create car with plate " + carDTO.getPlateNumber() + ". Car plate already exists.");
            throw new FleetGenericException("Can't create car with plate " + carDTO.getPlateNumber());
        }
        Car car = (Car) new DtoUtils().convertToEntity(new Car(), carDTO);
        repository.save(car);
        if (carDTO.getStaffMemberId() != null) {
            setStaffMember(car, carDTO);
        } else {
            azureBlobLoggingService.writeToLoggingFile("Saving car with plate" + carDTO.getPlateNumber() + ". No staff member with id " + carDTO.getStaffMemberId());
            throw new FleetGenericException("Can't create car with plate " + carDTO.getPlateNumber());
        }
        return (CarDTO) new DtoUtils().convertToDto(car, new CarDTO());
    }

    @Override
    @Transactional
    public CarDTO update(CarDTO carDTO) {
        Optional<Car> optionalCar = repository.findById(carDTO.getPlateNumber());
        if (optionalCar.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No car with plate " + carDTO.getPlateNumber());
            throw new FleetItemNotFoundException();
        } else {
            Car car = optionalCar.get();
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
    }

    /**
     * Check every day (or at startup) if there are terminated contracts and update car status
     * Dummy method
     */
    @Bean
    @Scheduled(fixedDelay = 86_400_000)
    public void updateCarList() {
        repository.selectFromCarWhereOngoing().forEach(car -> {
            if (car.getEndDate() != null && LocalDate.now().isAfter(car.getEndDate())) {
                car.setOngoing(false);
                repository.save(car);
                azureBlobLoggingService.writeToLoggingFile("Car " + car.getPlateNumber()
                        + " was archived, contract terminated " + car.getEndDate());
            }
        });
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<CarDTO> getAllCars(List<CarDTO> carDTOS) {
        for (Car car : repository.findAll()) {
            carDTOS.add(getCarDtoAndSetMemberId(car));
        }
        setInspectionId(carDTOS);
        return carDTOS;
    }

    private List<CarDTO> getAllActive(List<CarDTO> carDTOS) {
        List<Car> cars = repository.selectFromCarWhereOngoing();
        cars.forEach(car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        setInspectionId(carDTOS);
        return carDTOS;
    }

    private List<CarDTO> getAllArchived(List<CarDTO> carDTOS) {
        List<Car> cars = repository.selectFromCarWhereNotOngoing();
        cars.forEach(car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        setInspectionId(carDTOS);
        return carDTOS;
    }

    private List<CarDTO> getByBrand(List<CarDTO> carDTOS, String brandName) {
        Brand brand;
        try {
            brand = Brand.valueOf(brandName);
            List<Car> cars = repository.selectFromCarWhereBrandIs(brand.ordinal());
            cars.forEach(car -> {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            });
            setInspectionId(carDTOS);
            return carDTOS;
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("No brand was found with brand name " + brandName);
            throw new FleetItemNotFoundException();
        }
    }

    private List<CarDTO> getByFuel(List<CarDTO> carDTOS, String fuel) {
        FuelType fuelType;
        try {
            fuelType = FuelType.valueOf(fuel);
            List<Car> cars = repository.selectFromCarWhereFuelIs(fuelType.ordinal());
            cars.forEach(car -> {
                carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
            });
            setInspectionId(carDTOS);
            return carDTOS;
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("No fuel was found with fuel name " + fuel);
            throw new FleetItemNotFoundException();
        }
    }

    private List<CarDTO> getInspectables(List<CarDTO> carDTOS) {
        List<Car> cars = repository.selectFromCarWhereEndDateSmallerThanNowAndBiggerThan6MonthsAgoAndNoInspection();
        cars.forEach(car -> {
            carDTOS.add((CarDTO) new DtoUtils().convertToDto(car, new CarDTO()));
        });
        return carDTOS;
    }

    private List<CarDTO> filter(String filter, String option, List<CarDTO> carDTOS) {
        try {
            CarFilter carFilter = CarFilter.valueOf(filter);
            switch (carFilter) {
                case ALL:
                    return getAllCars(carDTOS);
                case ACTIVE:
                    return getAllActive(carDTOS);
                case ARCHIVED:
                    return getAllArchived(carDTOS);
                case FUEL:
                    return getByFuel(carDTOS, option);
                case BRAND:
                    return getByBrand(carDTOS, option);
                case INSPECTABLE:
                    return getInspectables(carDTOS);
                default:
                    throw new FleetGenericException();
            }
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("CAR Filter could not be applied: " + filter + " " + option);
            throw new FleetGenericException();
        }
    }

    // ------- DATA TRANSFORMATION -------

    /**
     * Set the staff member of a car
     * Throw an exception if staff member cannot be found
     * @param car the car to be saved or updated
     * @param carDTO the DTO of the car to create or update
     */
    private void setStaffMember(Car car, CarDTO carDTO) {
        Optional<StaffMember> optionalStaffMember = staffMemberRepository.findById(carDTO.getStaffMemberId());
        if (optionalStaffMember.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No staff member with id " + carDTO.getStaffMemberId());
            throw new FleetItemNotFoundException();
        } else {
            StaffMember staffMember = optionalStaffMember.get();
            car.setStaffMember(staffMember);
            staffMemberService.setCarOfStaffMember(staffMember.getStaffMemberId(), car.getPlateNumber());
            staffMemberRepository.save(staffMember);
            repository.save(car);
        }
    }

    /**
     * Add inspection ID to car DTO object that have an inspection related to them
     * @param carDTOS the objets to complete with inspection ID if relevant
     */
    private void setInspectionId(List<CarDTO> carDTOS) {
        carDTOS.forEach(carDTO -> {
            Optional<Inspection> optionalInspection = inspectionRepository.selectInspectionWhereCarPlateIs(carDTO.getPlateNumber());
            optionalInspection.ifPresent(inspection -> carDTO.setCarInspectionId(inspection.getCarInspectionId()));
        });
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
            return carDTO;
        } else {
            azureBlobLoggingService.writeToLoggingFile("Car " + carDTO.getPlateNumber()
                    + " has no staff member linked, or staff member " + carDTO.getStaffMemberId() + " could not be found");
            throw new FleetGenericException();
        }
    }
}
