package com.soprasteria.fleet.dto;

import com.soprasteria.fleet.dto.dtoUtils.DTOEntity;

public class LeasingCompanyDTO implements DTOEntity {
    private Integer leasingCompanyId;
    private String leasingCompanyName;
    private String leasingCompanyContactPerson;
    private String leasingCompanyPhone;
    private String leasingCompanyEmail;
    private boolean isActive;

    public LeasingCompanyDTO(Integer leasingCompanyId, String leasingCompanyName, String leasingCompanyContactPerson, String leasingCompanyPhone, String leasingCompanyEmail, boolean isActive) {
        this.leasingCompanyId = leasingCompanyId;
        this.leasingCompanyName = leasingCompanyName;
        this.leasingCompanyContactPerson = leasingCompanyContactPerson;
        this.leasingCompanyPhone = leasingCompanyPhone;
        this.leasingCompanyEmail = leasingCompanyEmail;
        this.isActive = isActive;
    }

    public LeasingCompanyDTO() {
    }

    @Override
    public String toString() {
        return "LeasingCompanyDTO{" +
                "leasingCompanyId=" + leasingCompanyId +
                ", leasingCompanyName='" + leasingCompanyName + '\'' +
                ", leasingCompanyContactPerson='" + leasingCompanyContactPerson + '\'' +
                ", leasingCompanyPhone='" + leasingCompanyPhone + '\'' +
                ", leasingCompanyEmail='" + leasingCompanyEmail + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}