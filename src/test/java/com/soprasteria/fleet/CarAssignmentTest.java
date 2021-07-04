package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.models.enums.Brand;
import com.soprasteria.fleet.models.enums.FuelType;
import com.soprasteria.fleet.models.enums.Language;
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

/**
 * Test class on car assignment to staff members
 * Check the results of a tank filling on data update
 * Some tests include data operations, therefore the context of the class is set to Transactional to rollback all operations after tests
 */

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

    /*
    Static creation of objects for test purpose
     */
    static StaffMember staffMember = new StaffMember(99999, "Dupont", "Jean", true, "jean@test.com", Language.FR, null);
    static CarDTO firstCar = new CarDTO("9-ZZZ-999", Brand.Audi, "A3", FuelType.DIESEL, 7.8, LocalDate.parse("2021-04-01"), null, null, 1, null, null);
    static CarDTO secondCar = new CarDTO("9-YYY-999", Brand.Citroen, "C5", FuelType.DIESEL, 5.0, LocalDate.now(), null, null, 1, null, null);

    /**
     * Check state of car and staff member after staff member without car is assigned a car
     * staffMember.hasCar == true
     * car.ongoing == true
     * car.staffMember == staffMember
     * current car of staffMember == car
     */
    @Test
    public void setCarDoesntHaveCar() {
        StaffMember staffMember = repository.save(CarAssignmentTest.staffMember);
        carService.create(firstCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), firstCar.getPlateNumber());
        Car car = carRepository.findById(firstCar.getPlateNumber()).get();
        assertTrue(staffMember.getHasCar());
        assertTrue(car.getOngoing());
        assertEquals(staffMember, car.getStaffMember());
        assertEquals(car.getPlateNumber(), service.getCurrentCarOfStaffMember(staffMember.getStaffMemberId()).getPlateNumber());
    }

    /**
     * Check state of car and staff member after staff member with a car is assigned a car
     * staffMember.hasCar == true
     * car2.ongoing == true
     * car1.ongoing == false
     * car2.staffMember == staffMember
     * car1.staffMember == staffMember (history of car ownership is retained)
     * current car of staffMember == car2
     * current car of staffMember != car1
     */
    @Test
    public void setCarHasCar() {
        StaffMember staffMember = repository.save(CarAssignmentTest.staffMember);
        carService.create(firstCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), firstCar.getPlateNumber());
        carService.create(secondCar);
        service.setCarOfStaffMember(staffMember.getStaffMemberId(), secondCar.getPlateNumber());
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
