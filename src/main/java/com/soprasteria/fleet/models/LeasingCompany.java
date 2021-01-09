package com.soprasteria.fleet.models;

import javax.persistence.*;

@Entity
@Table(name = "leasing_company")
public class LeasingCompany {

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
}
