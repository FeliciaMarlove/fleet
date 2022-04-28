package com.thecompany.fleet.services.utilServices;

import com.thecompany.fleet.errors.FleetGenericException;
import com.thecompany.fleet.models.Inspection;
import com.thecompany.fleet.models.enums.Language;
import com.thecompany.fleet.models.StaffMember;
import com.thecompany.fleet.models.TankFilling;
import com.thecompany.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import com.thecompany.fleet.services.utilServices.interfaces.EmailComposerService;
import com.thecompany.fleet.models.enums.DiscrepancyType;
import org.springframework.stereotype.Component;

@Component
public final class EmailComposerServiceImpl implements EmailComposerService {
    private final AzureBlobLoggingService azureBlobLoggingService;
    private final static String INSPECTION_BLOB_READ_ACCESS_TOKEN = "<TOKEN>";

    public EmailComposerServiceImpl(AzureBlobLoggingService azureBlobLoggingService) {
        this.azureBlobLoggingService = azureBlobLoggingService;
    }

    @Override
    public String writeEmailToFleetManagerAboutDiscrepancy(TankFilling tankFilling) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A discrepancy has been detected:")
                .append("\nDate: ").append(tankFilling.getDateTimeFilling())
                .append("\nCar: ").append(tankFilling.getCar().getPlateNumber())
                .append("\nStaff member: ").append(tankFilling.getCar().getStaffMember().getStaffFirstName())
                .append(" ").append(tankFilling.getCar().getStaffMember().getStaffLastName().toUpperCase())
                .append("\nDiscrepancy: ").append(tankFilling.getDiscrepancyType());

        switch (tankFilling.getDiscrepancyType()) {
            case DiscrepancyType.BEFORE_BIGGER_THAN_AFTER :
                stringBuilder.append("\nKm after: ")
                        .append(tankFilling.getKmAfter())
                        .append("\nKm before: ")
                        .append(tankFilling.getKmBefore());
                break;
            case DiscrepancyType.WRONG_FUEL:
                stringBuilder.append("\nFuel: ")
                        .append(tankFilling.getFuelType())
                        .append("\nCar Fuel: ")
                        .append(tankFilling.getCar().getFuelType());
                break;
            case DiscrepancyType.QUANTITY_TOO_HIGH:
                stringBuilder.append("\nConsumption: ")
                        .append(tankFilling.getConsumption())
                        .append("\nCar consumption: ")
                        .append(tankFilling.getCar().getAverageConsumption());
                break;
        }
        return stringBuilder.toString();
    }

    @Override
    public String writeEmailToStaffAboutInspection(Inspection inspection, StaffMember staffMember) {
        if (staffMember == null) {
            azureBlobLoggingService.writeToLoggingFile("An inspection e-mail couldn't be sent because staff member is null");
            throw new FleetGenericException("An inspection e-mail couldn't be sent because staff member is null");
        }
        Language lg = staffMember.getCommunicationLanguage();
        switch (lg) {
            case FR : //return writeFrench(inspection, staffMember); // TODO
            case NL : //return writeDutch(inspection, staffMember); // TODO
            case EN : default : return writeEnglish(inspection, staffMember);
        }
    }

    private String writeEnglish(Inspection inspection, StaffMember staffMember) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("To ")
                .append(staffMember.getStaffFirstName()).append(" ")
                .append(staffMember.getStaffLastName().toUpperCase())
                .append("\nThe inspection of your car ")
                .append(inspection.getCar().getPlateNumber());
        if(!inspection.isDamaged()) {
            stringBuilder.append(" revealed no damage.");
        } else {
            stringBuilder.append(" revealed some damage.")
                    .append("\nPlease consult the report at " + inspection.getInspectionReportFile() + INSPECTION_BLOB_READ_ACCESS_TOKEN);
            stringBuilder.append("\nYou will receive information about the financial implication in the coming days.");
        }
        stringBuilder.append("\nBest regards,\nThe fleet manager");
        return stringBuilder.toString();
    }

    private String writeDutch(Inspection inspection, StaffMember staffMember) {
        StringBuilder stringBuilder = new StringBuilder();
        // TODO
        return stringBuilder.toString();
    }

    private String writeFrench(Inspection inspection, StaffMember staffMember) {
        StringBuilder stringBuilder = new StringBuilder();
        // TODO
        return stringBuilder.toString();
    }
}
