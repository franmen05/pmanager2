package com.pmanager.service.impl;

import com.pmanager.domain.MedicalHistory;
import com.pmanager.repository.MedicalHistoryRepository;
import com.pmanager.service.MedicalHistoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalHistory}.
 */
@Service
@Transactional
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
    private final Logger log = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

    private final MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @Override
    public MedicalHistory save(MedicalHistory medicalHistory) {
        log.debug("Request to save MedicalHistory : {}", medicalHistory);
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public List<MedicalHistory> saveAll(List<MedicalHistory> medicalHistory) {
        log.debug("Request to save MedicalHistory : {}", medicalHistory);
        return medicalHistoryRepository.saveAll(medicalHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> findAll() {
        log.debug("Request to get all MedicalHistories");
        return medicalHistoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistory> findOne(Long id) {
        log.debug("Request to get MedicalHistory : {}", id);
        return medicalHistoryRepository.findById(id);
    }

    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<MedicalHistory> findAll(Long id) {
    //        log.debug("Request to get MedicalHistory : {}", id);
    //        return medicalHistoryRepository.findByRecordId(id);
    //    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> findByRecord(Long id) {
        log.debug("Request to get MedicalHistory : {}", id);
        return medicalHistoryRepository.findByRecordId(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalHistory : {}", id);
        medicalHistoryRepository.deleteById(id);
    }
}
