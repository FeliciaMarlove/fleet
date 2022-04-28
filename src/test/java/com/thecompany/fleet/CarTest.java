package com.thecompany.fleet;
import com.thecompany.fleet.models.Car;
import com.thecompany.fleet.models.LeasingCompany;
import com.thecompany.fleet.models.StaffMember;
import com.thecompany.fleet.models.enums.Brand;
import com.thecompany.fleet.models.enums.FuelType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class on car
 * Check data validation and sanitization
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FleetApplication.class})
@SpringBootTest
public final class CarTest {

    @Test
    public void sanitizePlateNumber() {
        String unsafeInput = "<script>alert(\"Injection!\")</script>1-AAA-000";
        String unsafeModel = "Abc<script>alert(\"Injection!\")</script>def";
        String unsafeFreeText = "Some information<script>alert(\"Injection!\")</script>";
        Car car1 = new Car();
        car1.setPlateNumber(unsafeInput);
        car1.setModel(unsafeModel);
        car1.setFreeText(unsafeFreeText);
        Car car2 = new Car(unsafeInput, null, Brand.Audi, unsafeModel, FuelType.FULL_ELECTRIC, null, LocalDate.now(), unsafeFreeText, new LeasingCompany(), new StaffMember());
        assertEquals("1-AAA-000", car1.getPlateNumber());
        assertEquals("1-AAA-000", car2.getPlateNumber());
        assertEquals("Abcdef", car1.getModel());
        assertEquals("Abcdef", car2.getModel());
        assertEquals("Some information", car1.getFreeText());
        assertEquals("Some information", car2.getFreeText());
    }
}
