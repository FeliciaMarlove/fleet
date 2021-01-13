package com.soprasteria.fleet.services;

import com.soprasteria.fleet.dto.StaffMemberDTO;
import com.soprasteria.fleet.dto.dtoUtils.DtoUtils;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import com.soprasteria.fleet.services.interfaces.StaffMemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffMemberServiceImpl implements StaffMemberService {
    private final StaffMemberRepository repository;

    public StaffMemberServiceImpl(StaffMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public StaffMemberDTO read(Integer staffMemberId) {
        StaffMember staffMember = repository.findById(staffMemberId).get();
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }

    @Override
    public List<StaffMemberDTO> readAll() {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        for(StaffMember staffMember: repository.findAll()) {
            staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
        }
        return staffMemberDTOS;
    }

    @Override
    public List<StaffMemberDTO> readAllWithCar() {
        List<StaffMemberDTO> staffMemberDTOS = new ArrayList<>();
        for(StaffMember staffMember: repository.findAll()) {
            if (staffMember.hasCar()) {
                staffMemberDTOS.add((StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO()));
            }
        }
        return staffMemberDTOS;
    }

    @Override
    public StaffMemberDTO update(StaffMemberDTO staffMemberDTO) {
        StaffMember staffMember = repository.findById(staffMemberDTO.getStaffMemberId()).get();
        staffMember.setHasCar(staffMemberDTO.hasCar());
        repository.save(staffMember);
        return (StaffMemberDTO) new DtoUtils().convertToDto(staffMember, new StaffMemberDTO());
    }
}
