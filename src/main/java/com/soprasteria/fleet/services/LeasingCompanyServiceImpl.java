package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.LeasingCompany;
import com.soprasteria.fleet.repositories.LeasingCompanyRepository;
import com.soprasteria.fleet.services.interfaces.LeasingCompanyService;
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
    public List<LeasingCompanyDTO> readAll() {
        List<LeasingCompanyDTO> leasingCompanies = new ArrayList<>();
        for(LeasingCompany leasingCompany: repository.findAll()) {
            leasingCompanies.add((LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO()));
        }
        return leasingCompanies;
    }

    @Override
    public List<LeasingCompanyDTO> readAllActive() {
        List<LeasingCompanyDTO> leasingCompanies = new ArrayList<>();
        for(LeasingCompany leasingCompany: repository.findAll()) {
            if (leasingCompany.isActive()) {
                leasingCompanies.add((LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO()));
            }
        }
        return leasingCompanies;
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
        leasingCompany.setActive(leasingCompanyDTO.isActive());
        leasingCompany.setLeasingCompanyContactPerson(leasingCompanyDTO.getLeasingCompanyContactPerson());
        leasingCompany.setLeasingCompanyEmail(leasingCompanyDTO.getLeasingCompanyEmail());
        leasingCompany.setLeasingCompanyName(leasingCompanyDTO.getLeasingCompanyName());
        leasingCompany.setLeasingCompanyPhone(leasingCompanyDTO.getLeasingCompanyPhone());
        repository.save(leasingCompany);
        return (LeasingCompanyDTO) new DtoUtils().convertToDto(leasingCompany, new LeasingCompanyDTO());
    }
}
