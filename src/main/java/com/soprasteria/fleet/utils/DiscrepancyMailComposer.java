package com.soprasteria.fleet.utils;

import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.models.TankFilling;

public class DiscrepancyMailComposer {
    private final TankFilling tankFilling;
    private final StaffMember staffMember;
    private StringBuilder stringBuilder = new StringBuilder();

    public DiscrepancyMailComposer(TankFilling tankFilling, StaffMember staffMember) {
        this.tankFilling = tankFilling;
        this.staffMember = staffMember;
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

    // TODO : voir meilleure méthode (html? plain string?)

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
