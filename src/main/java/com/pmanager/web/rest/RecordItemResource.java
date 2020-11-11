package com.pmanager.web.rest;

import com.pmanager.domain.RecordItem;
import com.pmanager.service.RecordItemService;
import com.pmanager.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.pmanager.domain.RecordItem}.
 */
@RestController
@RequestMapping("/api")
public class RecordItemResource {
    private final Logger log = LoggerFactory.getLogger(RecordItemResource.class);

    private static final String ENTITY_NAME = "recordItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecordItemService recordItemService;

    public RecordItemResource(RecordItemService recordItemService) {
        this.recordItemService = recordItemService;
    }

    /**
     * {@code POST  /record-items} : Create a new recordItem.
     *
     * @param recordItem the recordItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recordItem, or with status {@code 400 (Bad Request)} if the recordItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/record-items")
    public ResponseEntity<RecordItem> createRecordItem(@Valid @RequestBody RecordItem recordItem) throws URISyntaxException {
        log.debug("REST request to save RecordItem : {}", recordItem);
        if (recordItem.getId() != null) {
            throw new BadRequestAlertException("A new recordItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordItem result = recordItemService.save(recordItem);
        return ResponseEntity
            .created(new URI("/api/record-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /record-items} : Updates an existing recordItem.
     *
     * @param recordItem the recordItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recordItem,
     * or with status {@code 400 (Bad Request)} if the recordItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recordItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/record-items")
    public ResponseEntity<RecordItem> updateRecordItem(@Valid @RequestBody RecordItem recordItem) throws URISyntaxException {
        log.debug("REST request to update RecordItem : {}", recordItem);
        if (recordItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordItem result = recordItemService.save(recordItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recordItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /record-items} : get all the recordItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recordItems in body.
     */
    @GetMapping("/record-items")
    public List<RecordItem> getAllRecordItems() {
        log.debug("REST request to get all RecordItems");
        return recordItemService.findAll();
    }

    /**
     * {@code GET  /record-items/:id} : get the "id" recordItem.
     *
     * @param id the id of the recordItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recordItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/record-items/{id}")
    public ResponseEntity<RecordItem> getRecordItem(@PathVariable Long id) {
        log.debug("REST request to get RecordItem : {}", id);
        Optional<RecordItem> recordItem = recordItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recordItem);
    }

    /**
     * {@code DELETE  /record-items/:id} : delete the "id" recordItem.
     *
     * @param id the id of the recordItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/record-items/{id}")
    public ResponseEntity<Void> deleteRecordItem(@PathVariable Long id) {
        log.debug("REST request to delete RecordItem : {}", id);
        recordItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
