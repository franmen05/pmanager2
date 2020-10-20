package com.pmanager.service;

import com.pmanager.domain.MedicalHistoryConfig;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalHistoryConfig}.
 */
public interface MedicalHistoryConfigService {

    /**
     * Save a medicalHistoryConfig.
     *
     * @param medicalHistoryConfig the entity to save.
     * @return the persisted entity.
     */
    MedicalHistoryConfig save(MedicalHistoryConfig medicalHistoryConfig);

    /**
     * Get all the medicalHistoryConfigs.
     *
     * @return the list of entities.
     */
    List<MedicalHistoryConfig> findAll();


    /**
     * Get the "id" medicalHistoryConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalHistoryConfig> findOne(Long id);

    /**
     * Delete the "id" medicalHistoryConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
