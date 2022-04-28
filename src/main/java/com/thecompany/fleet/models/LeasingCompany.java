package com.soprasteria.fleet.models;

import com.soprasteria.fleet.services.utilServices.interfaces.Sanitizer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "leasing_company")
public final class LeasingCompany implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leasing_company_generator")
    @SequenceGenerator(name = "leasing_company_generator", allocationSize = 1, initialValue = 100)
    @Column(name = "leasing_id")
    private Integer leasingCompanyId;

    @Column(name = "leasing_name", unique = true, nullable = false)
    private String leasingCompanyName;

    @Column(name = "leasing_contact")
    private String leasingCompanyContactPerson;

    @Column(name = "leasing_phone")
    private String leasingCompanyPhone;

    @Column(name = "leasing_email")
    private String leasingCompanyEmail;

    @Column(name = "is_active")
    private Boolean active;

    public Integer getLeasingCompanyId() {
        return leasingCompanyId;
    }

    public void setLeasingCompanyId(Integer leasingCompanyId) {
        this.leasingCompanyId = leasingCompanyId;
    }

    public String getLeasingCompanyName() {
        return leasingCompanyName;
    }

    public void setLeasingCompanyName(String leasingCompanyName) {
        this.leasingCompanyName = Sanitizer.stripXSS(leasingCompanyName);
    }

    public String getLeasingCompanyContactPerson() {
        return leasingCompanyContactPerson;
    }

    public void setLeasingCompanyContactPerson(String leasingCompanyContactPerson) {
        this.leasingCompanyContactPerson = Sanitizer.stripXSS(leasingCompanyContactPerson);
    }

    public String getLeasingCompanyPhone() {
        return leasingCompanyPhone;
    }

    public void setLeasingCompanyPhone(String leasingCompanyPhone) {
        this.leasingCompanyPhone = leasingCompanyPhone != null ? Sanitizer.stripXSS(leasingCompanyPhone).replaceAll("\\s","") : null;
    }

    public String getLeasingCompanyEmail() {
        return leasingCompanyEmail;
    }

    public void setLeasingCompanyEmail(String leasingCompanyEmail) {
        this.leasingCompanyEmail = Sanitizer.stripXSS(leasingCompanyEmail);
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LeasingCompany(Integer leasingCompanyId, String leasingCompanyName, String leasingCompanyContactPerson, String leasingCompanyPhone, String leasingCompanyEmail) {
        this();
        this.leasingCompanyId = leasingCompanyId;
        this.leasingCompanyName = Sanitizer.stripXSS(leasingCompanyName);
        this.leasingCompanyContactPerson = Sanitizer.stripXSS(leasingCompanyContactPerson);
        this.leasingCompanyPhone = Sanitizer.stripXSS(leasingCompanyPhone);
        this.leasingCompanyEmail = Sanitizer.stripXSS(leasingCompanyEmail);
    }

    public LeasingCompany() {
        this.active = true;
    }

    @Override
    public String toString() {
        return "LeasingCompany : " + this.getLeasingCompanyId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeasingCompany that = (LeasingCompany) o;
        return leasingCompanyId.equals(that.leasingCompanyId) ||
                leasingCompanyName.equals(that.leasingCompanyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leasingCompanyId);
    }
}
