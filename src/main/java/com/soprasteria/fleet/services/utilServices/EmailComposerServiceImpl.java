package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.services.interfaces.EmailComposerService;

public class EmailComposerServiceImpl implements EmailComposerService {

    @Override
    public String writeEmailToFleetManagerAboutDiscrepancy(TankFilling tankFilling) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A discrepancy has been detected:")
                .append("\nDate: " + tankFilling.getDateTimeFilling())
                .append("\nCar: " + tankFilling.getCar().getPlateNumber())
                .append("\nStaff member: " + tankFilling.getCar().getStaffMember().getStaffFirstName() + " " + tankFilling.getCar().getStaffMember().getStaffLastName().toUpperCase())
                .append("\nDiscrepancy: " + tankFilling.getDiscrepancyType());

        switch (tankFilling.getDiscrepancyType()) {
            case BEFORE_BIGGER_THAN_AFTER :
                stringBuilder.append("\nKm after: " + tankFilling.getKmAfter())
                        .append("\nKm before: " + tankFilling.getKmBefore());
                break;
            case WRONG_FUEL:
                stringBuilder.append("\nFuel: " + tankFilling.getFuelType())
                        .append("\nCar Fuel: " + tankFilling.getCar().getFuelType());
                break;
            case QUANTITY_TOO_HIGH:
                stringBuilder.append("\nConsumption: " + tankFilling.getConsumption())
                        .append("\nCar consumption: " + tankFilling.getCar().getAverageConsumption());
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
        stringBuilder.append("A l'attention de " + staffMember.getStaffFirstName() + " " + staffMember.getStaffLastName().toUpperCase());
        // TODO
    }

    private void writeEnglish(StringBuilder stringBuilder, StaffMember staffMember) {
        // TODO
    }
}
