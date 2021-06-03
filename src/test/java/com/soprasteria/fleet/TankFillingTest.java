package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.CarDTO;
import com.soprasteria.fleet.dto.TankFillingDTO;
import com.soprasteria.fleet.enums.Brand;
import com.soprasteria.fleet.enums.DiscrepancyType;
import com.soprasteria.fleet.enums.FuelType;
import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.CarService;
import com.soprasteria.fleet.services.businessServices.interfaces.StaffMemberService;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FleetApplication.class})
@SpringBootTest
@Transactional
public class TankFillingTest {
    @Autowired
    private TankFillingService service;

    @Autowired
    private CarService carService;

    @Autowired
    private StaffMemberService staffMemberService;

    @Autowired
    private StaffMemberRepository staffMemberRepository;

    static CarDTO carDTO = new CarDTO("0-AAA-000", Brand.Audi, "A3", FuelType.DIESEL, 4.4, LocalDate.now(), null, false, null, 1, null, null);
    static TankFillingDTO tankFillingDtoNoDiscrepancy = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", null, null, null);
    static TankFillingDTO tankFillingDtoFuelIsGasoline = new TankFillingDTO(0, null, 500, null, null, LocalDateTime.now(), FuelType.GASOLINE, 25.6, "0-AAA-000", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter50 = new TankFillingDTO(0, null, 50, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter505 = new TankFillingDTO(0, null, 505, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", null, null, null);
    static TankFillingDTO tankFillingDtoKmAfter1000 = new TankFillingDTO(0, null, 5000, null, null, LocalDateTime.now(), FuelType.DIESEL, 25.6, "0-AAA-000", null, null, null);
    static StaffMember staffMember = new StaffMember(1, "Dupont", "Jean", true, "jean@test.com", Language.FR, 7);

    @BeforeEach
    public void createStaffMember() {
        carDTO.setStaffMemberId(staffMemberRepository.save(staffMember).getStaffMemberId());
    }

    /**
     * Un plein de carburant (result2) actualise le nombre de kilomètres actuel de la voiture (car)
     * Après le plein
     * le kilométrage de fin du plein précédent (result1) = le kilométrage de départ du nouveau plein
     * le kilométrage total de la voiture = le kilométrage de départ du nouveau plein
     */
    @Test
    public void setKilometersBefore() {
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDtoNoDiscrepancy);
        assertNotNull(result1);
        assertEquals(0, result1.getKmBefore());
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertNotNull(result2);
        assertEquals(result1.getKmAfter(), result2.getKmBefore());
        assertEquals(carService.read(car.getPlateNumber()).getKilometers(), result2.getKmAfter());
    }

    /**
     * Après un plein de carburant (result) où le type de carburant ne correspond pas au type de carburant de la voiture (car)
     * type d'erreur (DiscrepancyType) == WRONG FUEL
     * discrepancy == true
     * nombre d'erreurs du staffMember += 1
     */
    @Test
    public void identifyWrongFuelDiscrepancy() {
        Integer discrepanciesBefore = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        carService.create(carDTO);
        TankFillingDTO result = service.create(tankFillingDtoFuelIsGasoline);
        assertNotNull(result);
        Integer discrepanciesAfter = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        assertEquals(discrepanciesBefore, discrepanciesAfter - 1);
        assertEquals(DiscrepancyType.WRONG_FUEL, result.getDiscrepancyType());
        assertEquals(true, result.getDiscrepancy());
    }

    /**
     * Après un plein de carburant (result2) où le kilométrage de départ est plus élevé que le kilométrage de départ du plein précédent (result1)
     * type d'erreur (DiscrepancyType) == BEFORE_BIGGER_THAN_AFTER
     * discrepancy == true
     * nombre d'erreurs du staffMember += 1
     */
    @Test
    public void identifyBeforeBiggerThanAfterDiscrepancy() {
        Integer discrepanciesBefore = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDtoNoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter50);
        assertNotNull(result1);
        assertNotNull(result2);
        Integer discrepanciesAfter = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        assertEquals(discrepanciesBefore, discrepanciesAfter - 1);
        assertEquals(DiscrepancyType.BEFORE_BIGGER_THAN_AFTER, result2.getDiscrepancyType());
    }

    /**
     * Après un plein de carburant (result2) où le kilométrage par rapport au plein précédent (result1) n'est pas cohérent
     * Sur base de la comparaison de la consommation effective et de la consommation théorique de la voiture (car)
     * type d'erreur (DiscrepancyType) == QUANTITY_TOO_HIGH
     * discrepancy == true
     * nombre d'erreurs du staffMember += 1
     */
    @Test
    public void identifyQuantityTooHighDiscrepancy() {
        Integer discrepanciesBefore = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDtoNoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter505);
        assertNotNull(result1);
        assertNotNull(result2);
        Integer discrepanciesAfter = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        assertEquals(discrepanciesBefore, discrepanciesAfter - 1);
        assertEquals(DiscrepancyType.QUANTITY_TOO_HIGH, result2.getDiscrepancyType());
    }

    /**
     * Après un plein de carburant (result2) qui ne présente aucun type d'erreur
     * type d'erreur (DiscrepancyType) == null
     * discrepancy == false
     * nombre d'erreurs du staffMember inchangé
     */
    @Test
    public void setNoDiscrepancy() {
        Integer discrepanciesBefore = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        CarDTO car = carService.create(carDTO);
        TankFillingDTO result1 = service.create(tankFillingDtoNoDiscrepancy);
        TankFillingDTO result2 = service.create(tankFillingDtoKmAfter1000);
        assertNotNull(result1);
        assertNotNull(result2);
        Integer discrepanciesAfter = staffMemberService.read(carDTO.getStaffMemberId()).getNumberDiscrepancies();
        assertNull(result2.getDiscrepancyType());
        assertFalse(result2.getDiscrepancy());
        assertEquals(discrepanciesBefore, discrepanciesAfter);
    }
}
