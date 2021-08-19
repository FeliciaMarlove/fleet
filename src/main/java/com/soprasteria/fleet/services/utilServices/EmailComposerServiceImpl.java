package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.models.Inspection;
import com.soprasteria.fleet.models.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;
import com.soprasteria.fleet.services.utilServices.interfaces.EmailComposerService;
import org.springframework.stereotype.Component;

@Component
public final class EmailComposerServiceImpl implements EmailComposerService {
    private final AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl;

    public EmailComposerServiceImpl(AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl) {
        this.azureBlobLoggingServiceImpl = azureBlobLoggingServiceImpl;
    }

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

    @Override
    public String writeEmailToStaffAboutInspection(Inspection inspection, StaffMember staffMember) {
        if (staffMember == null) {
            azureBlobLoggingServiceImpl.writeToLoggingFile("An inspection e-mail couldn't be sent because staff member is null");
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
                    .append("\nPlease consult the report at this address ")
                    .append("<a href=\"" + inspection.getInspectionReportFile() + "\">Report</a>")
                    .append("\n and attached pictures:");
            String[] pictures = inspection.getPicturesFiles().split(",");
            for (String pic: pictures) {
                stringBuilder.append("\n<a href=\"" + pic + "\"> Picture </a>");
            }
            stringBuilder.append("\nYou will receive information about the financial implication in the coming days");
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
