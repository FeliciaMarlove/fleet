package com.soprasteria.fleet.models;

import com.soprasteria.fleet.models.enums.Language;
import com.soprasteria.fleet.services.utilServices.interfaces.Sanitizer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "staff_member")
public final class StaffMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_member_generator")
    @SequenceGenerator(name = "staff_member_generator", allocationSize = 1, initialValue = 100)
    @Column(name = "staff_id")
    private Integer staffMemberId;

    @Column(name = "staff_name", nullable = false)
    private String staffLastName;

    @Column(name = "staff_firstname", nullable = false)
    private String staffFirstName;

    @Column(name = "has_car", nullable = false)
    private Boolean hasCar;

    @Column(name = "corporate_email", /*unique = true,*/ nullable = false)
    // env de développement : not unique pour pouvoir affecter mon adresse mail à tous les staff members
    private String corporateEmail;

    @Column(name = "language", nullable = false)
    private Language communicationLanguage;

    @Column(name = "discrepancies", nullable = false)
    private Integer numberDiscrepancies;

    @OneToMany(mappedBy = "staffMember", targetEntity = Car.class)
    List<Car> cars = new ArrayList<>();

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
        this.staffLastName = Sanitizer.stripXSS(staffLastName);
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public void setStaffFirstName(String staffFirstName) {
        this.staffFirstName = Sanitizer.stripXSS(staffFirstName);
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
        this.corporateEmail = Sanitizer.stripXSS(corporateEmail);
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

    public void setNumberDiscrepancies(Integer numberActualDiscrepancies) {
        this.numberDiscrepancies = numberActualDiscrepancies;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public StaffMember(Integer staffMemberId, String staffLastName, String staffFirstName, Boolean hasCar, String corporateEmail, Language communicationLanguage, Integer numberDiscrepancies) {
        this.staffMemberId = staffMemberId;
        this.staffLastName = staffLastName;
        this.staffFirstName = staffFirstName;
        this.hasCar = hasCar;
        this.corporateEmail = corporateEmail;
        this.communicationLanguage = communicationLanguage;
        this.numberDiscrepancies = numberDiscrepancies;
    }

    public StaffMember(String staffLastName, String staffFirstName, Boolean hasCar, String corporateEmail, Language communicationLanguage) {
        this();
        this.staffLastName = Sanitizer.stripXSS(staffLastName);
        this.staffFirstName = Sanitizer.stripXSS(staffFirstName);
        this.hasCar = hasCar;
        this.corporateEmail = Sanitizer.stripXSS(corporateEmail);
        this.communicationLanguage = communicationLanguage;
    }

    public StaffMember() {
        numberDiscrepancies = 0;
    }

    @Override
    public String toString() {
        return "StaffMember : " + this.getStaffMemberId();
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
