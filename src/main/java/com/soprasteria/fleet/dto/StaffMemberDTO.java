package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;
import com.soprasteria.fleet.enums.Language;

public class StaffMemberDTO implements DTOEntity {
    private Integer staffMemberId;
    private String staffLastName;
    private String staffFirstName;
    private Boolean hasCar;
    private String corporateEmail;
    private Language communicationLanguage;
    private Integer numberActualDiscrepancies;

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

    public Boolean hasCar() {
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

    public Integer getNumberActualDiscrepancies() {
        return numberActualDiscrepancies;
    }

    public void setNumberActualDiscrepancies(Integer numberActualDiscrepancies) {
        this.numberActualDiscrepancies = numberActualDiscrepancies;
    }

    public StaffMemberDTO(Integer staffMemberId, String staffLastName, String staffFirstName, Boolean hasCar, String corporateEmail, Language communicationLanguage, Integer numberActualDiscrepancies) {
        this.staffMemberId = staffMemberId;
        this.staffLastName = staffLastName;
        this.staffFirstName = staffFirstName;
        this.hasCar = hasCar;
        this.corporateEmail = corporateEmail;
        this.communicationLanguage = communicationLanguage;
        this.numberActualDiscrepancies = numberActualDiscrepancies;
    }

    public StaffMemberDTO() {
    }

    @Override
    public String toString() {
        return "leasingCompanyDTO{" +
                "staffMemberId=" + staffMemberId +
                ", staffLastName='" + staffLastName + '\'' +
                ", staffFirstName='" + staffFirstName + '\'' +
                ", hasCar=" + hasCar +
                ", corporateEmail='" + corporateEmail + '\'' +
                ", communicationLanguage=" + communicationLanguage +
                ", numberActualDiscrepancies=" + numberActualDiscrepancies +
                '}';
    }
}
