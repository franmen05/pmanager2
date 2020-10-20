package com.pmanager.service.mapper;


import com.pmanager.domain.*;
import com.pmanager.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {


    @Mapping(target = "records", ignore = true)
    @Mapping(target = "removeRecord", ignore = true)
    @Mapping(target = "turns", ignore = true)
    @Mapping(target = "removeTurn", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
