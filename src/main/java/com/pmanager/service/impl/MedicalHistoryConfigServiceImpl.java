package com.pmanager.service.impl;

import com.pmanager.service.MedicalHistoryConfigService;
import com.pmanager.domain.MedicalHistoryConfig;
import com.pmanager.repository.MedicalHistoryConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicalHistoryConfig}.
 */
@Service
@Transactional
public class MedicalHistoryConfigServiceImpl implements MedicalHistoryConfigService {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryConfigServiceImpl.class);

    private final MedicalHistoryConfigRepository medicalHistoryConfigRepository;

    public MedicalHistoryConfigServiceImpl(MedicalHistoryConfigRepository medicalHistoryConfigRepository) {
        this.medicalHistoryConfigRepository = medicalHistoryConfigRepository;
    }

    @Override
    public MedicalHistoryConfig save(MedicalHistoryConfig medicalHistoryConfig) {
        log.debug("Request to save MedicalHistoryConfig : {}", medicalHistoryConfig);
        return medicalHistoryConfigRepository.save(medicalHistoryConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistoryConfig> findAll() {
        log.debug("Request to get all MedicalHistoryConfigs");
        return medicalHistoryConfigRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistoryConfig> findOne(Long id) {
        log.debug("Request to get MedicalHistoryConfig : {}", id);
        return medicalHistoryConfigRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalHistoryConfig : {}", id);
        medicalHistoryConfigRepository.deleteById(id);
    }
}
