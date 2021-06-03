package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.CarRepository;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FleetApplication.class})
@SpringBootTest
@Transactional
public class CarAssignmentTest {
    @Autowired
    private StaffMemberService service;

    @Autowired
    private StaffMemberRepository repository;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    static StaffMember staffMember = new StaffMember(1, "Dupont", "Jean", true, "jean@test.com", Language.FR, null);
    static CarDTO firstCar = new CarDTO("0-AAA-666", Brand.Audi, "A3", FuelType.DIESEL, 7.8, LocalDate.parse("2021-04-01"), null, false, null, 1, null, null);
    static CarDTO secondCar = new CarDTO("0-AAA-777", Brand.Citroen, "C5", FuelType.DIESEL, 5.0, LocalDate.now(), null, false, null, 1, null, null);

    @Test
    public void setCarDoesntHaveCar() {
        StaffMember staffMember = repository.save(CarAssignmentTest.staffMember);
        carService.create(firstCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), firstCar.getPlateNumber());
        Car car = carRepository.findById(firstCar.getPlateNumber()).get();
        staffMember = repository.findById(staffMember.getStaffMemberId()).get();
        assertTrue(staffMember.getHasCar());
        assertTrue(car.getOngoing());
        assertEquals(staffMember, car.getStaffMember());
        assertEquals(car.getPlateNumber(), service.getCurrentCarOfStaffMember(staffMember.getStaffMemberId()).getPlateNumber());
    }

    @Test
    public void setCarHasCar() {
        StaffMember staffMember = repository.save(CarAssignmentTest.staffMember);
        carService.create(firstCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), firstCar.getPlateNumber());
        carService.create(secondCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), secondCar.getPlateNumber());
        staffMember = repository.findById(staffMember.getStaffMemberId()).get();
        Car car1 = carRepository.findById(firstCar.getPlateNumber()).get();
        Car car2 = carRepository.findById(secondCar.getPlateNumber()).get();
        assertTrue(staffMember.getHasCar());
        assertTrue(car2.getOngoing());
        assertFalse(car1.getOngoing());
        assertEquals(staffMember, car2.getStaffMember());
        assertEquals(staffMember, car1.getStaffMember());
        assertEquals(car2.getPlateNumber(), service.getCurrentCarOfStaffMember(staffMember.getStaffMemberId()).getPlateNumber());
        assertNotEquals(car1.getPlateNumber(), service.getCurrentCarOfStaffMember(staffMember.getStaffMemberId()).getPlateNumber());
    }
}
