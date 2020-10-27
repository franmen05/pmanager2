package com.pmanager.service.impl;

import com.pmanager.domain.Turn;
import com.pmanager.domain.enumeration.Status;
import com.pmanager.repository.TurnRepository;
import com.pmanager.service.TurnService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (turn.getStatus() == Status.REGISTER) {
            LocalDateTime date = LocalDateTime.now().withHour(1);
            //            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            //        String myDate = date.format(dtf);

            val instant = date.toInstant(ZoneOffset.ofHours(-4));
            val turns = turnRepository.findAllByCreateDateAfter(instant);
            val lastTurn = turns.stream().max(Comparator.comparing(Turn::getCreateDate));
            //        var tun=turns.stream().max((o1, o2) -> o1.getCreateDate().compareTo(o2.getCreateDate()));

            lastTurn.ifPresent(t -> turn.setPosition(t.getPosition() + 1));
        }

        return turnRepository.save(turn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Turn> findAll() {
        log.debug("Request to get all Turns");
        return turnRepository.findAll();
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
