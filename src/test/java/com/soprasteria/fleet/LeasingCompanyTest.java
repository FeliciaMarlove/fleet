package com.soprasteria.fleet;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.models.LeasingCompany;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test class on leasing company
 * Check data validation and sanitization
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FleetApplication.class})
@SpringBootTest
public class LeasingCompanyTest {

    static LeasingCompany leasingCompany = new LeasingCompany(99999, "Test company", "Contact perso", "050225588", "somemail@test.com");
    static LeasingCompanyDTO leasingCompanyDTO = new LeasingCompanyDTO(99999, "Test company", "Contact perso", "050225588", "somemail@test.com", true);

    /**
     * Phone number is saved as a string. Front-end client may send phone number with white spaces in it.
     * The back-end service must save leasing company objects' phone number without any white space.
     */
    @Test
    public void stripPhoneNumber() {
        leasingCompany.setLeasingCompanyPhone("060 22 33 44");
        assertEquals("060223344", leasingCompany.getLeasingCompanyPhone());
        leasingCompanyDTO.setLeasingCompanyPhone("060 22 33 44");
        assertEquals("060223344", leasingCompanyDTO.getLeasingCompanyPhone());
        leasingCompany.setLeasingCompanyPhone("0 80 2 2 3 34 4");
        assertEquals("080223344", leasingCompany.getLeasingCompanyPhone());
        leasingCompanyDTO.setLeasingCompanyPhone("0 8 0 2 2 3 3 4   4");
        assertEquals("080223344", leasingCompanyDTO.getLeasingCompanyPhone());
    }
}
