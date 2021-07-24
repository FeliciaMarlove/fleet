package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.models.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailComposerService;
import org.springframework.stereotype.Component;

@Component
public final class EmailComposerServiceImpl implements EmailComposerService {

    @Override
    public String writeEmailToFleetManagerAboutDiscrepancy(TankFilling tankFilling) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean staffMember = tankFilling.getCar().getStaffMember() != null;
        stringBuilder.append("A discrepancy has been detected:")
                .append("\nDate: ")
                .append(tankFilling.getDateTimeFilling())
                .append("\nCar: ")
                .append(tankFilling.getCar().getPlateNumber())
                .append("\nStaff member: ")
                .append(staffMember ? tankFilling.getCar().getStaffMember().getStaffFirstName() : "{a problem occurred while retrieving the staff member")
                .append(" ")
                .append(staffMember ? tankFilling.getCar().getStaffMember().getStaffLastName().toUpperCase() : "")
                .append("\nDiscrepancy: ")
                .append(tankFilling.getDiscrepancyType());

        switch (tankFilling.getDiscrepancyType()) {
            case BEFORE_BIGGER_THAN_AFTER :
                stringBuilder.append("\nKm after: ")
                        .append(tankFilling.getKmAfter())
                        .append("\nKm before: ")
                        .append(tankFilling.getKmBefore());
                break;
            case WRONG_FUEL:
                stringBuilder.append("\nFuel: ")
                        .append(tankFilling.getFuelType())
                        .append("\nCar Fuel: ")
                        .append(tankFilling.getCar().getFuelType());
                break;
            case QUANTITY_TOO_HIGH:
                stringBuilder.append("\nConsumption: ")
                        .append(tankFilling.getConsumption())
                        .append("\nCar consumption: ")
                        .append(tankFilling.getCar().getAverageConsumption());
                break;
        }
        return stringBuilder.toString();
    }

    /*
    // TODO
    deal with end of contract without damage and with inspection
     */
    @Override
    public String writeEmailToStaffAboutInspection(StaffMember staffMember, boolean isInspection) {
        if (staffMember == null) {
            throw new FleetGenericException("An inspection e-mail couldn't be sent because staff member is null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        Language lg = staffMember.getCommunicationLanguage();
        switch (lg) {
            case FR : writeFrench(stringBuilder, staffMember);
            break;
            case NL : writeDutch(stringBuilder, staffMember);
            break;
            case EN : default : writeEnglish(stringBuilder, staffMember);
            break;
        }
        return stringBuilder.toString();
    }


    private void writeDutch(StringBuilder stringBuilder, StaffMember staffMember) {
        // TODO
    }

    private void writeFrench(StringBuilder stringBuilder, StaffMember staffMember) {
        stringBuilder.append("A l'attention de ")
                .append(staffMember.getStaffFirstName()).append(" ")
                .append(staffMember.getStaffLastName().toUpperCase());
        // TODO
    }

    private void writeEnglish(StringBuilder stringBuilder, StaffMember staffMember) {
        // TODO
    }
}
