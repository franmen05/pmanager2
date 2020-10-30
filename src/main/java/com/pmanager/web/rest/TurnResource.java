package com.pmanager.web.rest;

import com.pmanager.domain.Turn;
import com.pmanager.service.TurnService;
import com.pmanager.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pmanager.domain.Turn}.
 */
@RestController
@RequestMapping("/api")
public class TurnResource {

    private final Logger log = LoggerFactory.getLogger(TurnResource.class);

    private static final String ENTITY_NAME = "turn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TurnService turnService;

    public TurnResource(TurnService turnService) {
        this.turnService = turnService;
    }

    /**
     * {@code POST  /turns} : Create a new turn.
     *
     * @param turn the turn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new turn, or with status {@code 400 (Bad Request)} if the turn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/turns")
    public ResponseEntity<Turn> createTurn(@Valid @RequestBody Turn turn) throws URISyntaxException {
        log.debug("REST request to save Turn : {}", turn);
        if (turn.getId() != null) {
            throw new BadRequestAlertException("A new turn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Turn result = turnService.save(turn);
        return ResponseEntity.created(new URI("/api/turns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /turns} : Updates an existing turn.
     *
     * @param turn the turn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turn,
     * or with status {@code 400 (Bad Request)} if the turn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the turn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/turns")
    public ResponseEntity<Turn> updateTurn(@Valid @RequestBody Turn turn) throws URISyntaxException {
        log.debug("REST request to update Turn : {}", turn);
        if (turn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Turn result = turnService.save(turn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, turn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /turns} : get all the turns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of turns in body.
     */
    @GetMapping("/turns")
    public ResponseEntity<List<Turn>> getAllTurns(Pageable pageable) {
        log.debug("REST request to get a page of Turns");
        Page<Turn> page = turnService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /turns/:id} : get the "id" turn.
     *
     * @param id the id of the turn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the turn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/turns/{id}")
    public ResponseEntity<Turn> getTurn(@PathVariable Long id) {
        log.debug("REST request to get Turn : {}", id);
        Optional<Turn> turn = turnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turn);
    }

    /**
     * {@code DELETE  /turns/:id} : delete the "id" turn.
     *
     * @param id the id of the turn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/turns/{id}")
    public ResponseEntity<Void> deleteTurn(@PathVariable Long id) {
        log.debug("REST request to delete Turn : {}", id);
        turnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
