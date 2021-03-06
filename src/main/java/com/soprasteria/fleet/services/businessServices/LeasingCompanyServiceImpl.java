package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.enums.filters.LeasingFilter;
import com.soprasteria.fleet.models.LeasingCompany;
import com.soprasteria.fleet.repositories.LeasingCompanyRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.LeasingCompanyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeasingCompanyServiceImpl implements LeasingCompanyService {
    private final LeasingCompanyRepository repository;

    public LeasingCompanyServiceImpl(LeasingCompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public LeasingCompanyDTO read(Integer leasingCompanyId) {
        LeasingCompany leasingCompany = repository.findById(leasingCompanyId).get();
        return (LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO());
    }

    @Override
    public List<LeasingCompanyDTO> read(String filter, String option) {
        List<LeasingCompanyDTO> leasingCompanies = new ArrayList<>();
        return filter(filter, option, leasingCompanies);
    }

    @Override
    public LeasingCompanyDTO create(LeasingCompanyDTO leasingCompanyDTO) {
        LeasingCompany leasingCompany = (LeasingCompany) new DtoUtils().convertToEntity(new LeasingCompany(), leasingCompanyDTO);
        repository.save(leasingCompany);
        return (LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO());
    }

    @Override
    public String delete(Integer leasingCompanyId) {
        LeasingCompany leasingCompany = repository.findById(leasingCompanyId).get();
        leasingCompany.setActive(false);
        repository.save(leasingCompany);
        return leasingCompany.getLeasingCompanyName() + " was set inactive";
    }

    @Override
    public LeasingCompanyDTO update(LeasingCompanyDTO leasingCompanyDTO) {
        LeasingCompany leasingCompany = repository.findById(leasingCompanyDTO.getLeasingCompanyId()).get();
        if (leasingCompanyDTO.isActive() != null) {
            leasingCompany.setActive(leasingCompanyDTO.isActive());
        }
        if (leasingCompanyDTO.getLeasingCompanyContactPerson() != null) {
            leasingCompany.setLeasingCompanyContactPerson(leasingCompanyDTO.getLeasingCompanyContactPerson());
        }
        if (leasingCompanyDTO.getLeasingCompanyEmail() != null) {
            leasingCompany.setLeasingCompanyEmail(leasingCompanyDTO.getLeasingCompanyEmail());
        }
        if (leasingCompanyDTO.getLeasingCompanyName() != null) {
            leasingCompany.setLeasingCompanyName(leasingCompanyDTO.getLeasingCompanyName());
        }
        if (leasingCompanyDTO.getLeasingCompanyPhone() != null) {
            leasingCompany.setLeasingCompanyPhone(leasingCompanyDTO.getLeasingCompanyPhone());
        }
        repository.save(leasingCompany);
        return (LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO());
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<LeasingCompanyDTO> filter(String filter, String option, List<LeasingCompanyDTO> leasingCompanyDTOS) {
        try {
            LeasingFilter leasingFilter = LeasingFilter.valueOf(filter);
            switch (leasingFilter) {
                case ALL: default: return getAll(leasingCompanyDTOS);
                case ACTIVE: return readAllActive(leasingCompanyDTOS);
            }
        } catch (Exception e) {
            System.out.println(e);
            return getAll(leasingCompanyDTOS);
        }
    }

    private List<LeasingCompanyDTO> getAll(List<LeasingCompanyDTO> leasingCompanyDTOS) {
        for(LeasingCompany leasingCompany: repository.findAll()) {
            leasingCompanyDTOS.add((LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO()));
        }
        return leasingCompanyDTOS;
    }

    private List<LeasingCompanyDTO> readAllActive(List<LeasingCompanyDTO> leasingCompanyDTOS) {
        for(LeasingCompany leasingCompany: repository.selectFromLeasingCompanyWhereActiveTrue()) {
                leasingCompanyDTOS.add((LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO()));
        }
        return leasingCompanyDTOS;
    }
}
