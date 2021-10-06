package com.pmanager.web.rest;

import com.pmanager.domain.MedicalHistory;
import com.pmanager.domain.Record;
import com.pmanager.service.MedicalHistoryService;
import com.pmanager.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.pmanager.domain.MedicalHistory}.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryResource {
    private final Logger log = LoggerFactory.getLogger(MedicalHistoryResource.class);

    private static final String ENTITY_NAME = "medicalHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryResource(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /**
     * {@code POST  /medical-histories} : Create a new medicalHistory.
     *
     * @param medicalHistory the medicalHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalHistory, or with status {@code 400 (Bad Request)} if the medicalHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-history")
    public ResponseEntity<MedicalHistory> createMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory)
        throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() != null) {
            throw new BadRequestAlertException("A new medicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalHistory result = medicalHistoryService.save(medicalHistory);
        return ResponseEntity
            .created(new URI("/api/medical-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/medical-histories")
    public ResponseEntity<List<MedicalHistory>> createMedicalHistory(@Valid @RequestBody List<MedicalHistory> medicalHistory)
        throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistory);
        //        if (medicalHistory.getId() != null) {
        //            throw new BadRequestAlertException("A new medicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        //        }
        val result = medicalHistoryService.saveAll(medicalHistory);
        val id = result
            .stream()
            .findFirst()
            .map(medicalHistory1 -> medicalHistory1.getRecord().getId())
            .orElseThrow(() -> new BadRequestAlertException("Can't save medical history ", "", ""));
        return ResponseEntity
            .created(new URI("/api/medical-histories/" + id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-histories} : Updates an existing medicalHistory.
     *
     * @param medicalHistory the medicalHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalHistory,
     * or with status {@code 400 (Bad Request)} if the medicalHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-histories")
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory)
        throws URISyntaxException {
        log.debug("REST request to update MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalHistory result = medicalHistoryService.save(medicalHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicalHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-histories} : get all the medicalHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalHistories in body.
     */
    @GetMapping("/medical-histories")
    public List<MedicalHistory> getAllMedicalHistories() {
        log.debug("REST request to get all MedicalHistories");
        return medicalHistoryService.findAll();
    }

    /**
     * {@code GET  /medical-histories/:id} : get the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-histories/{id}")
    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        Optional<MedicalHistory> medicalHistory = medicalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalHistory);
    }

    //    @GetMapping("/medical-histories-all/{id}")
    //    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistory(@PathVariable Long id) {
    //        log.debug("REST request to get MedicalHistory : {}", id);
    //        val medicalHistory = medicalHistoryService.findOne(id);
    //        return ResponseUtil.wrapOrNotFound(medicalHistory);
    //    }

    @GetMapping("/medical-histories/record/{id}")
    public List<MedicalHistory> getMedicalHistoryByRecord(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        //        val medicalHistory =
        //        return ResponseEntity
        //            .created(new URI("/api/medical-histories/" + id))
        //            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, id.toString()))
        //            .body(medicalHistory);
        return medicalHistoryService.findByRecord(id);
    }

    /**
     * {@code DELETE  /medical-histories/:id} : delete the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-histories/{id}")
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistory : {}", id);
        medicalHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
