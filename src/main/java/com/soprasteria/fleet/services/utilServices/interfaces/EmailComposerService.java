package com.soprasteria.fleet.services.utilServices.interfaces;

import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;

public interface EmailComposerService {
    String writeEmailToFleetManagerAboutDiscrepancy(TankFilling tankFilling);
    String writeEmailToStaffAboutInspection(StaffMember staffMember, boolean isInspection);
}
