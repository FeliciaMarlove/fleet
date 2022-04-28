package com.thecompany.fleet.dto;

import com.thecompany.fleet.dto.dtoUtils.DTOEntity;
import com.thecompany.fleet.models.enums.Language;

public final class StaffMemberDTO implements DTOEntity {
    private Integer staffMemberId;
    private String staffLastName;
    private String staffFirstName;
    private Boolean hasCar;
    private String corporateEmail;
    private Language communicationLanguage;
    private Integer numberDiscrepancies;

    public Integer getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(Integer staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    public String getStaffLastName() {
        return staffLastName;
    }

    public void setStaffLastName(String staffLastName) {
        this.staffLastName = staffLastName;
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public void setStaffFirstName(String staffFirstName) {
        this.staffFirstName = staffFirstName;
    }

    public Boolean getHasCar() {
        return hasCar;
    }

    public void setHasCar(Boolean hasCar) {
        this.hasCar = hasCar;
    }

    public String getCorporateEmail() {
        return corporateEmail;
    }

    public void setCorporateEmail(String corporateEmail) {
        this.corporateEmail = corporateEmail;
    }

    public Language getCommunicationLanguage() {
        return communicationLanguage;
    }

    public void setCommunicationLanguage(Language communicationLanguage) {
        this.communicationLanguage = communicationLanguage;
    }

    public Integer getNumberDiscrepancies() {
        return numberDiscrepancies;
    }

    public void setNumberDiscrepancies(Integer numberDiscrepancies) {
        this.numberDiscrepancies = numberDiscrepancies;
    }

    public StaffMemberDTO(Integer staffMemberId, String staffLastName, String staffFirstName, Boolean hasCar, String corporateEmail, Language communicationLanguage, Integer numberDiscrepancies) {
        this.staffMemberId = staffMemberId;
        this.staffLastName = staffLastName;
        this.staffFirstName = staffFirstName;
        this.hasCar = hasCar;
        this.corporateEmail = corporateEmail;
        this.communicationLanguage = communicationLanguage;
        this.numberDiscrepancies = numberDiscrepancies;
    }

    public StaffMemberDTO() {
        this.numberDiscrepancies = 0;
    }

    @Override
    public String toString() {
        return "staffMemberDTO : " + this.getStaffMemberId();
    }
}
