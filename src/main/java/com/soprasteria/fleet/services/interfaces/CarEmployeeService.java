package com.soprasteria.fleet.services.interfaces;

import com.soprasteria.fleet.dto.CarEmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface CarEmployeeService {
    /*
    un employé a toujours 0-1 relation active (même si hasCar il peut être dans la période de flottement entre deux voitures) -> peut renvoyer du null
     */
    Optional<CarEmployeeDTO> read(Integer staffMemberId);
    /*
    une voiture a toujours 1 relation avec 1 staff member, même si la relation est archivée
     */
    CarEmployeeDTO read(String plateNumber);
    List<CarEmployeeDTO> readAll();
    CarEmployeeDTO create(CarEmployeeDTO carEmployeeDTO);
    String delete(Integer staffMemberId);
    String delete(String plateNumber);
    CarEmployeeDTO update(CarEmployeeDTO carEmployeeDTO);
}
