package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.models.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailComposerService;
import org.springframework.stereotype.Component;

@Component
public final class EmailComposerServiceImpl implements EmailComposerService {
    private final AzureBlobLoggingService azureBlobLoggingService;
    private final static String INSPECTION_BLOB_READ_ACCESS_TOKEN = "?sp=r&st=2021-10-10T11:06:15Z&se=2021-11-30T20:06:15Z&sv=2020-08-04&sr=c&sig=RreBS4JHzW0u4R3fIpVZIPDlEmfIh49uk3Z5dyCUido%3D";

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
