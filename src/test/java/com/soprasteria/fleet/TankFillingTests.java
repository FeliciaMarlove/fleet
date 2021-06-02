package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.models.Car;
import com.soprasteria.fleet.models.TankFilling;
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

    @Test
    public void identifyWrongFuelDiscrepancy() {
        CarDTO carDTO = new CarDTO("0-AAA-000", Brand.Audi, "A3", FuelType.DIESEL, 2.5, LocalDate.now(), null, false, null, 1, 1, null);
        carService.create(carDTO);
        TankFillingDTO tankFillingDTO = new TankFillingDTO(0, 0, 500, null, null, LocalDateTime.now(), FuelType.GASOLINE, 25.6, "0-AAA-000", 2.5, null, null);
        TankFillingDTO result = service.create(tankFillingDTO);
        assertNotNull(result);
        assertEquals(DiscrepancyType.WRONG_FUEL, result.getDiscrepancyType());
        assertEquals(true, result.getDiscrepancy());
    }
}
