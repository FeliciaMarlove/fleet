package com.soprasteria.fleet.services.businessServices;

import com.soprasteria.fleet.dto.LeasingCompanyDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.models.enums.filters.LeasingFilter;
import com.soprasteria.fleet.models.LeasingCompany;
import com.soprasteria.fleet.repositories.LeasingCompanyRepository;
import com.soprasteria.fleet.services.businessServices.interfaces.LeasingCompanyService;
import com.soprasteria.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public final class LeasingCompanyServiceImpl implements LeasingCompanyService {
    private final LeasingCompanyRepository repository;
    private final AzureBlobLoggingService azureBlobLoggingService;

    public LeasingCompanyServiceImpl(LeasingCompanyRepository repository, AzureBlobLoggingService azureBlobLoggingService) {
        this.repository = repository;
        this.azureBlobLoggingService = azureBlobLoggingService;
    }

    @Override
    public LeasingCompanyDTO read(Integer leasingCompanyId) {
        Optional<LeasingCompany> optionalLeasingCompany = repository.findById(leasingCompanyId);
        if (optionalLeasingCompany.isPresent()) {
            return (LeasingCompanyDTO) new DtoUtils().convertToDto(optionalLeasingCompany.get(), new LeasingCompanyDTO());
        } else {
            azureBlobLoggingService.writeToLoggingFile("No leasing company was found with it " + leasingCompanyId);
            throw new FleetItemNotFoundException();
        }
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
        Optional<LeasingCompany> optionalLeasingCompany = repository.findById(leasingCompanyId);
        if (optionalLeasingCompany.isPresent()) {
            LeasingCompany leasingCompany = optionalLeasingCompany.get();
            leasingCompany.setActive(false);
            repository.save(leasingCompany);
            return leasingCompany.getLeasingCompanyName() + " was set inactive";
        }
        azureBlobLoggingService.writeToLoggingFile("Soft delete failed. No leasing company was found with id " + leasingCompanyId);
        throw new FleetGenericException();
    }

    @Override
    public LeasingCompanyDTO update(LeasingCompanyDTO leasingCompanyDTO) throws FleetItemNotFoundException {
        Optional<LeasingCompany> optionalLeasingCompany = repository.findById(leasingCompanyDTO.getLeasingCompanyId());
        if (optionalLeasingCompany.isEmpty()) {
            azureBlobLoggingService.writeToLoggingFile("No leasing company was found with id " + leasingCompanyDTO.getLeasingCompanyId());
            throw new FleetItemNotFoundException();
        } else {
            LeasingCompany leasingCompany = optionalLeasingCompany.get();
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
    }

    /* PRIVATE METHODS */

    // ------- FILTERING -------

    private List<LeasingCompanyDTO> filter(String filter, String option, List<LeasingCompanyDTO> leasingCompanyDTOS) {
        try {
            LeasingFilter leasingFilter = LeasingFilter.valueOf(filter);
            switch (leasingFilter) {
                case ALL: return getAll(leasingCompanyDTOS);
                case ACTIVE: return readAllActive(leasingCompanyDTOS);
                default: throw new FleetGenericException();
            }
        } catch (Exception e) {
            azureBlobLoggingService.writeToLoggingFile("LEASING COMPANY Filter could not be applied: " + filter + option);
            throw new FleetGenericException();
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
