package com.soprasteria.fleet.models;

import com.soprasteria.fleet.enums.Language;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "staff_member")
public class StaffMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_member_generator")
    @SequenceGenerator(name = "staff_member_generator", allocationSize = 1)
    @Column(name = "staff_id")
    private Integer staffMemberId;

    @Column(name = "staff_name")
    private String staffLastName;

    @Column(name = "staff_firstname")
    private String staffFirstName;

    @Column(name = "has_car")
    private Boolean hasCar;

    @Column(name = "corporate_email")
    private String corporateEmail;

    @Column(name = "language")
    private Language communicationLanguage;

    @Column(name = "actual_discrepancies")
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

    public Boolean isHasCar() {
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

    public StaffMember(Integer staffMemberId, String staffLastName, String staffFirstName, Boolean hasCar, String corporateEmail, Language communicationLanguage, Integer numberActualDiscrepancies) {
        this.staffMemberId = staffMemberId;
        this.staffLastName = staffLastName;
        this.staffFirstName = staffFirstName;
        this.hasCar = hasCar;
        this.corporateEmail = corporateEmail;
        this.communicationLanguage = communicationLanguage;
        this.numberActualDiscrepancies = numberActualDiscrepancies;
    }

    public StaffMember() {
    }

    @Override
    public String toString() {
        return "StaffMember{" +
                "staffMemberId=" + staffMemberId +
                ", staffLastName='" + staffLastName + '\'' +
                ", staffFirstName='" + staffFirstName + '\'' +
                ", hasCar=" + hasCar +
                ", corporateEmail='" + corporateEmail + '\'' +
                ", communicationLanguage=" + communicationLanguage +
                ", numberDiscrepancies=" + numberActualDiscrepancies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffMember that = (StaffMember) o;
        return staffMemberId.equals(that.staffMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffMemberId);
    }
}
