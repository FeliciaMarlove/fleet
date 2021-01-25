package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;

public class EmailComposerService {
    private StaffMember staffMember;
    private StringBuilder stringBuilder = new StringBuilder();

    public String writeEmailToStaffAboutInspection() {
        Language lg = staffMember.getCommunicationLanguage();
        switch (lg) {
            case FR : writeFrench(); break;
            case NL : writeNederlands(); break;
            case EN : default : writeEnglish(); break;
        }
        return stringBuilder.toString();
    }


    private void writeNederlands() {
        // TODO
    }

    private void writeFrench() {
        stringBuilder.append("A l'attention de " + staffMember.getStaffFirstName() + " " + staffMember.getStaffLastName().toUpperCase());
        // TODO
    }

    private void writeEnglish() {
        // TODO
    }
}
