package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.TankFillingService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class TankFillingTests {
    @Autowired
    private TankFillingService service;

    @Autowired
    private CarService carService;

    static CarDTO carDTO = new CarDTO("0-AAA-000", Brand.Audi, "A3", FuelType.DIESEL, 2.5, LocalDate.now(), null, false, null, 1, 1, null);
    static TankFillingDTO tankFillingDTONoDiscrepancy = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", 2.5, null, null);
    static TankFillingDTO tankFillingDtoFuelIsGasoline = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.GASOLINE, 25.6, "0-AAA-000", 2.5, null, null);
    static TankFillingDTO tankFillingDtoKmAfter50 = new TankFillingDTO(0, null, 50, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", 2.5, null, null);
    static TankFillingDTO tankFillingDtoKmAfter505 = new TankFillingDTO(0, null, 505, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", 2.5, null, null);
    static TankFillingDTO tankFillingDtoKmAfter1000 = new TankFillingDTO(0, null, 5000, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", 2.5, null, null);

    @Test
    public void setKilometersBefore() {
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDTONoDiscrepancy);
        assertNotNull(result1);
        assertEquals(0, result1.getKmBefore());
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertNotNull(result2);
        assertEquals(result1.getKmAfter(), result2.getKmBefore());
        assertEquals(carService.read(car.getPlateNumber()).getKilometers(), result2.getKmAfter());
    }


    @Test
    public void identifyWrongFuelDiscrepancy() {
        carService.create(carDTO);
        TankFillingDTO result = service.create(tankFillingDtoFuelIsGasoline);
        assertNotNull(result);
        assertEquals(DiscrepancyType.WRONG_FUEL, result.getDiscrepancyType());
        assertEquals(true, result.getDiscrepancy());
    }

    @Test
    public void identifyBeforeBiggerThanAfterDiscrepancy() {
        carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDTONoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertEquals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER, result2.getDiscrepancyType());
    }

    @Test
    public void identifyQuantityTooHighDiscrepancy() {
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDTONoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter505);
        assertNotNull(result2);
        assertEquals(DiscrepancyType.QUANTITY_TOO_HIGH, result2.getDiscrepancyType());
    }

    @Test
    public void setNoDiscrepancy() {
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDTONoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter1000);
        assertNotNull(result2);
        assertNull(result2.getDiscrepancyType());
    }
}
