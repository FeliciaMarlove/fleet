package com.soprasteria.fleet.utils;

import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;

public class MailComposer {
    private StaffMember staffMember;
    private StringBuilder stringBuilder = new StringBuilder();

    public MailComposer(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public MailComposer() {
    }

    public String writeEmail() {
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
