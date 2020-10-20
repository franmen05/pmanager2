package com.pmanager.web.rest;

import com.pmanager.domain.MedicalHistoryConfig;
import com.pmanager.service.MedicalHistoryConfigService;
import com.pmanager.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pmanager.domain.MedicalHistoryConfig}.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryConfigResource {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryConfigResource.class);

    private static final String ENTITY_NAME = "medicalHistoryConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalHistoryConfigService medicalHistoryConfigService;

    public MedicalHistoryConfigResource(MedicalHistoryConfigService medicalHistoryConfigService) {
        this.medicalHistoryConfigService = medicalHistoryConfigService;
    }

    /**
     * {@code POST  /medical-history-configs} : Create a new medicalHistoryConfig.
     *
     * @param medicalHistoryConfig the medicalHistoryConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalHistoryConfig, or with status {@code 400 (Bad Request)} if the medicalHistoryConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-history-configs")
    public ResponseEntity<MedicalHistoryConfig> createMedicalHistoryConfig(@Valid @RequestBody MedicalHistoryConfig medicalHistoryConfig) throws URISyntaxException {
        log.debug("REST request to save MedicalHistoryConfig : {}", medicalHistoryConfig);
        if (medicalHistoryConfig.getId() != null) {
            throw new BadRequestAlertException("A new medicalHistoryConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalHistoryConfig result = medicalHistoryConfigService.save(medicalHistoryConfig);
        return ResponseEntity.created(new URI("/api/medical-history-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-history-configs} : Updates an existing medicalHistoryConfig.
     *
     * @param medicalHistoryConfig the medicalHistoryConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalHistoryConfig,
     * or with status {@code 400 (Bad Request)} if the medicalHistoryConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalHistoryConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-history-configs")
    public ResponseEntity<MedicalHistoryConfig> updateMedicalHistoryConfig(@Valid @RequestBody MedicalHistoryConfig medicalHistoryConfig) throws URISyntaxException {
        log.debug("REST request to update MedicalHistoryConfig : {}", medicalHistoryConfig);
        if (medicalHistoryConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalHistoryConfig result = medicalHistoryConfigService.save(medicalHistoryConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicalHistoryConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-history-configs} : get all the medicalHistoryConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalHistoryConfigs in body.
     */
    @GetMapping("/medical-history-configs")
    public List<MedicalHistoryConfig> getAllMedicalHistoryConfigs() {
        log.debug("REST request to get all MedicalHistoryConfigs");
        return medicalHistoryConfigService.findAll();
    }

    /**
     * {@code GET  /medical-history-configs/:id} : get the "id" medicalHistoryConfig.
     *
     * @param id the id of the medicalHistoryConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalHistoryConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-history-configs/{id}")
    public ResponseEntity<MedicalHistoryConfig> getMedicalHistoryConfig(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistoryConfig : {}", id);
        Optional<MedicalHistoryConfig> medicalHistoryConfig = medicalHistoryConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalHistoryConfig);
    }

    /**
     * {@code DELETE  /medical-history-configs/:id} : delete the "id" medicalHistoryConfig.
     *
     * @param id the id of the medicalHistoryConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-history-configs/{id}")
    public ResponseEntity<Void> deleteMedicalHistoryConfig(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistoryConfig : {}", id);
        medicalHistoryConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
