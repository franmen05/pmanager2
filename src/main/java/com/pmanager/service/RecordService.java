package com.pmanager.service;

import com.pmanager.domain.Record;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Record}.
 */
public interface RecordService {

    /**
     * Save a record.
     *
     * @param record the entity to save.
     * @return the persisted entity.
     */
    Record save(Record record);

    /**
     * Get all the records.
     *
     * @return the list of entities.
     */
    List<Record> findAll();


    /**
     * Get the "id" record.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Record> findOne(Long id);

    /**
     * Delete the "id" record.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
