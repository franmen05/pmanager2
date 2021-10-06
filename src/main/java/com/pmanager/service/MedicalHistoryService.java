package com.pmanager.service;

import com.pmanager.domain.MedicalHistory;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalHistory}.
 */
public interface MedicalHistoryService {
    /**
     * Save a medicalHistory.
     *
     * @param medicalHistory the entity to save.
     * @return the persisted entity.
     */
    MedicalHistory save(MedicalHistory medicalHistory);
    List<MedicalHistory> saveAll(List<MedicalHistory> medicalHistory);

    /**
     * Get all the medicalHistories.
     *
     * @return the list of entities.
     */
    List<MedicalHistory> findAll();
    //    List<MedicalHistory> findAll(Long id);

    /**
     * Get the "id" medicalHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalHistory> findOne(Long id);
    List<MedicalHistory> findByRecord(Long id);

    /**
     * Delete the "id" medicalHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
