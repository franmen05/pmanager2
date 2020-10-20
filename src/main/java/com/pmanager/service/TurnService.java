package com.pmanager.service;

import com.pmanager.domain.Turn;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Turn}.
 */
public interface TurnService {

    /**
     * Save a turn.
     *
     * @param turn the entity to save.
     * @return the persisted entity.
     */
    Turn save(Turn turn);

    /**
     * Get all the turns.
     *
     * @return the list of entities.
     */
    List<Turn> findAll();


    /**
     * Get the "id" turn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Turn> findOne(Long id);

    /**
     * Delete the "id" turn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
