package com.pmanager.service.impl;

import com.pmanager.service.TurnService;
import com.pmanager.domain.Turn;
import com.pmanager.repository.TurnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Turn}.
 */
@Service
@Transactional
public class TurnServiceImpl implements TurnService {

    private final Logger log = LoggerFactory.getLogger(TurnServiceImpl.class);

    private final TurnRepository turnRepository;

    public TurnServiceImpl(TurnRepository turnRepository) {
        this.turnRepository = turnRepository;
    }

    @Override
    public Turn save(Turn turn) {
        log.debug("Request to save Turn : {}", turn);
        return turnRepository.save(turn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Turn> findAll(Pageable pageable) {
        log.debug("Request to get all Turns");
        return turnRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Turn> findOne(Long id) {
        log.debug("Request to get Turn : {}", id);
        return turnRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Turn : {}", id);
        turnRepository.deleteById(id);
    }
}
