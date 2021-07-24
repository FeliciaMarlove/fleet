package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

public final class LeasingCompanyDTO implements DTOEntity {
    private Integer leasingCompanyId;
    private String leasingCompanyName;
    private String leasingCompanyContactPerson;
    private String leasingCompanyPhone;
    private String leasingCompanyEmail;
    private Boolean active;

    public LeasingCompanyDTO(Integer leasingCompanyId, String leasingCompanyName, String leasingCompanyContactPerson, String leasingCompanyPhone, String leasingCompanyEmail) {
        this();
        this.leasingCompanyId = leasingCompanyId;
        this.leasingCompanyName = leasingCompanyName;
        this.leasingCompanyContactPerson = leasingCompanyContactPerson;
        this.leasingCompanyPhone = leasingCompanyPhone;
        this.leasingCompanyEmail = leasingCompanyEmail;
    }

    public LeasingCompanyDTO() {
        this.active = true;
    }

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
        this.leasingCompanyPhone = leasingCompanyPhone != null ? leasingCompanyPhone.replaceAll("\\s","") : null;
    }

    public String getLeasingCompanyEmail() {
        return leasingCompanyEmail;
    }

    public void setLeasingCompanyEmail(String leasingCompanyEmail) {
        this.leasingCompanyEmail = leasingCompanyEmail;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "LeasingCompanyDTO{" +
                "leasingCompanyId=" + leasingCompanyId +
                ", leasingCompanyName='" + leasingCompanyName + '\'' +
                ", leasingCompanyContactPerson='" + leasingCompanyContactPerson + '\'' +
                ", leasingCompanyPhone='" + leasingCompanyPhone + '\'' +
                ", leasingCompanyEmail='" + leasingCompanyEmail + '\'' +
                ", isActive=" + active +
                '}';
    }
}
