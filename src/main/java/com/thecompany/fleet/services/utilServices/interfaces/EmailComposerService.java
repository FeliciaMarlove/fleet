package com.thecompany.fleet.services.utilServices.interfaces;

import com.thecompany.fleet.models.Inspection;
import com.thecompany.fleet.models.StaffMember;
import com.thecompany.fleet.models.TankFilling;

public interface EmailComposerService {
    String writeEmailToFleetManagerAboutDiscrepancy(TankFilling tankFilling);
    String writeEmailToStaffAboutInspection(Inspection inspection, StaffMember staffMember);
}
