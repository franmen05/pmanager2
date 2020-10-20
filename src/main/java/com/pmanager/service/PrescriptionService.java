package com.pmanager.service;

import com.pmanager.domain.Prescription;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Prescription}.
 */
public interface PrescriptionService {

    /**
     * Save a prescription.
     *
     * @param prescription the entity to save.
     * @return the persisted entity.
     */
    Prescription save(Prescription prescription);

    /**
     * Get all the prescriptions.
     *
     * @return the list of entities.
     */
    List<Prescription> findAll();


    /**
     * Get the "id" prescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prescription> findOne(Long id);

    /**
     * Delete the "id" prescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
