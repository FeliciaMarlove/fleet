package com.soprasteria.fleet.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "leasing_company")
public class LeasingCompany implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leasing_company_generator")
    @SequenceGenerator(name = "leasing_company_generator", allocationSize = 1)
    @Column(name = "leasing_id")
    private Integer leasingCompanyId;

    @Column(name = "leasing_name")
    private String leasingCompanyName;

    @Column(name = "leasing_contact")
    private String leasingCompanyContactPerson;

    @Column(name = "leasing_phone")
    private String leasingCompanyPhone;

    @Column(name = "leasing_email")
    private String leasingCompanyEmail;

    @Column(name = "is_active")
    private boolean isActive;

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
        this.leasingCompanyName = leasingCompanyName;
    }

    public String getLeasingCompanyContactPerson() {
        return leasingCompanyContactPerson;
    }

    public void setLeasingCompanyContactPerson(String leasingCompanyContactPerson) {
        this.leasingCompanyContactPerson = leasingCompanyContactPerson;
    }

    public String getLeasingCompanyPhone() {
        return leasingCompanyPhone;
    }

    public void setLeasingCompanyPhone(String leasingCompanyPhone) {
        this.leasingCompanyPhone = leasingCompanyPhone;
    }

    public String getLeasingCompanyEmail() {
        return leasingCompanyEmail;
    }

    public void setLeasingCompanyEmail(String leasingCompanyEmail) {
        this.leasingCompanyEmail = leasingCompanyEmail;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LeasingCompany(Integer leasingCompanyId, String leasingCompanyName, String leasingCompanyContactPerson, String leasingCompanyPhone, String leasingCompanyEmail) {
        this();
        this.leasingCompanyId = leasingCompanyId;
        this.leasingCompanyName = leasingCompanyName;
        this.leasingCompanyContactPerson = leasingCompanyContactPerson;
        this.leasingCompanyPhone = leasingCompanyPhone;
        this.leasingCompanyEmail = leasingCompanyEmail;
    }

    public LeasingCompany() {
        this.isActive = true;
    }

    @Override
    public String toString() {
        return "LeasingCompany{" +
                "leasingCompanyId=" + leasingCompanyId +
                ", leasingCompanyName='" + leasingCompanyName + '\'' +
                ", leasingCompanyContactPerson='" + leasingCompanyContactPerson + '\'' +
                ", leasingCompanyPhone='" + leasingCompanyPhone + '\'' +
                ", leasingCompanyEmail='" + leasingCompanyEmail + '\'' +
                ", isActive=" + isActive +
                '}';
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
        return Objects.hash(leasingCompanyId, leasingCompanyName);
    }
}
