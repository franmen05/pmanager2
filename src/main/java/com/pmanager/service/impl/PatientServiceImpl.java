package com.pmanager.service.impl;

import com.pmanager.domain.Patient;
import com.pmanager.domain.Record;
import com.pmanager.repository.PatientRepository;
import com.pmanager.service.PatientService;
import com.pmanager.service.RecordService;
import com.pmanager.service.dto.PatientDTO;
import com.pmanager.service.mapper.PatientMapper;
import java.util.Optional;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final RecordService recordService;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper, RecordService recordService) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.recordService = recordService;
    }

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = persist(patientDTO);
        return patientMapper.toDto(patient);
    }

    private Patient persist(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patient;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        val patient = this.persist(patientDTO);
        recordService.save(Record.create(patient));
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
