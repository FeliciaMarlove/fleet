package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.models.enums.Brand;
import com.soprasteria.fleet.models.enums.DiscrepancyType;
import com.soprasteria.fleet.models.enums.FuelType;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.TankFillingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Test class on tank filling
 * Check the results of a tank filling
 * Some tests include data operations, therefore the context of the class is set to Transactional to rollback all operations after tests
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FleetApplication.class})
@SpringBootTest
@Transactional
public class TankFillingTest {
    @Autowired
    private TankFillingService service;

    @Autowired
    private CarService carService;

    /*
    Static creation of objects for test purpose
     */
    static CarDTO carDTO = new CarDTO("8-XYZ-123", Brand.Audi, "A3", FuelType.DIESEL, 4.4, LocalDate.now(), null, null, 1, null, null);
    static TankFillingDTO tankFillingDtoBase = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "8-XYZ-123", null, null, null);
    static TankFillingDTO tankFillingDtoFuelIsGasoline = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.GASOLINE, 25.6, "8-XYZ-123", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter50 = new TankFillingDTO(0, null, 50, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "8-XYZ-123", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter505 = new TankFillingDTO(0, null, 505, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "8-XYZ-123", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter1000 = new TankFillingDTO(0, null, 5000, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "8-XYZ-123", null, null, null);

    /**
     * Create the car before each test
     */
    @BeforeEach
    private void createCar() {
        carService.create(carDTO);
    }

    /**
     * Check car kilometers update after a tank filling
     * Start kilometers of new tank filling = end kilometers of previous tank filling
     * Current kilometers of car = end kilometers of new tank filling
     */
    @Test
    public void setKilometersBefore() {
        TankFillingDTO result1 = service.create(tankFillingDtoBase);
        assertNotNull(result1);
        assertEquals(0, result1.getKmBefore());
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertNotNull(result2);
        assertEquals(result1.getKmAfter(), result2.getKmBefore());
        assertEquals(carService.read(carDTO.getPlateNumber()).getKilometers(), result2.getKmAfter());
    }

    /**
     * Check result if tank filling fuel type != car fuel type
     * discrepancy == true
     * discrepancy type == WRONG FUEL
     */
    @Test
    public void identifyWrongFuelDiscrepancy() {
        TankFillingDTO result = service.create(tankFillingDtoFuelIsGasoline);
        assertNotNull(result);
        assertEquals(DiscrepancyType.WRONG_FUEL, result.getDiscrepancyType());
        assertEquals(true, result.getDiscrepancy());
    }

    /**
     * Check result if new tank filling end kilometers < previous tank filling end kilometers
     * discrepancy == true
     * discrepancy type == BEFORE_BIGGER_THAN_AFTER
     */
    @Test
    public void identifyBeforeBiggerThanAfterDiscrepancy() {
        TankFillingDTO result1 = service.create(tankFillingDtoBase);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER, result2.getDiscrepancyType());
    }

    /**
     * Check result if average consumption between two tank fillings is higher than allowed consumption
     * discrepancy == true
     * discrepancy type == QUANTITY_TOO_HIGH
     */
    @Test
    public void identifyQuantityTooHighDiscrepancy() {
        TankFillingDTO result1 = service.create(tankFillingDtoBase);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter505);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(DiscrepancyType.QUANTITY_TOO_HIGH, result2.getDiscrepancyType());
    }

    /**
     * Check result if there is no discrepancy
     * discrepancy == false
     * discrepancy type == null
     */
    @Test
    public void setNoDiscrepancy() {
        TankFillingDTO result1 = service.create(tankFillingDtoBase);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter1000);
        assertNotNull(result1);
        assertNotNull(result2);
        assertNull(result2.getDiscrepancyType());
        assertFalse(result2.getDiscrepancy());
    }

    /**
     * Check the consumption calculation between two tank fillings
     * consumption = (liters * 100) / (km after - km before), as double, rounded
     */
    @Test
    public void getAverageConsumptionAllowed() {
        service.create(tankFillingDtoBase);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter505);
        double consumption = (result2.getLiters() * 100) / (result2.getKmAfter() - result2.getKmBefore());
        double roundedConsumption = (double)(Math.round(consumption * 100) / 100);
        assertEquals(roundedConsumption, result2.getConsumption());
    }
}
