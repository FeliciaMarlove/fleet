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
}
