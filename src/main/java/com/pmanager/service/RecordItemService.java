package com.pmanager.service;

import com.pmanager.domain.RecordItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RecordItem}.
 */
public interface RecordItemService {

    /**
     * Save a recordItem.
     *
     * @param recordItem the entity to save.
     * @return the persisted entity.
     */
    RecordItem save(RecordItem recordItem);

    /**
     * Get all the recordItems.
     *
     * @return the list of entities.
     */
    List<RecordItem> findAll();


    /**
     * Get the "id" recordItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecordItem> findOne(Long id);

    /**
     * Delete the "id" recordItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
