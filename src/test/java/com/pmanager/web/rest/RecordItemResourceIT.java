package com.pmanager.web.rest;

import com.pmanager.PmanagerApp;
import com.pmanager.domain.RecordItem;
import com.pmanager.repository.RecordItemRepository;
import com.pmanager.service.RecordItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecordItemResource} REST controller.
 */
@SpringBootTest(classes = PmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecordItemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecordItemRepository recordItemRepository;

    @Autowired
    private RecordItemService recordItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecordItemMockMvc;

    private RecordItem recordItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecordItem createEntity(EntityManager em) {
        RecordItem recordItem = new RecordItem()
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE);
        return recordItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecordItem createUpdatedEntity(EntityManager em) {
        RecordItem recordItem = new RecordItem()
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        return recordItem;
    }

    @BeforeEach
    public void initTest() {
        recordItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecordItem() throws Exception {
        int databaseSizeBeforeCreate = recordItemRepository.findAll().size();
        // Create the RecordItem
        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isCreated());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeCreate + 1);
        RecordItem testRecordItem = recordItemList.get(recordItemList.size() - 1);
        assertThat(testRecordItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecordItem.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRecordItem.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createRecordItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordItemRepository.findAll().size();

        // Create the RecordItem with an existing ID
        recordItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setDescription(null);

        // Create the RecordItem, which fails.


        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setLastUpdateDate(null);

        // Create the RecordItem, which fails.


        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordItems() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        // Get all the recordItemList
        restRecordItemMockMvc.perform(get("/api/record-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recordItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRecordItem() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        // Get the recordItem
        restRecordItemMockMvc.perform(get("/api/record-items/{id}", recordItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recordItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(DEFAULT_LAST_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecordItem() throws Exception {
        // Get the recordItem
        restRecordItemMockMvc.perform(get("/api/record-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecordItem() throws Exception {
        // Initialize the database
        recordItemService.save(recordItem);

        int databaseSizeBeforeUpdate = recordItemRepository.findAll().size();

        // Update the recordItem
        RecordItem updatedRecordItem = recordItemRepository.findById(recordItem.getId()).get();
        // Disconnect from session so that the updates on updatedRecordItem are not directly saved in db
        em.detach(updatedRecordItem);
        updatedRecordItem
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restRecordItemMockMvc.perform(put("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecordItem)))
            .andExpect(status().isOk());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeUpdate);
        RecordItem testRecordItem = recordItemList.get(recordItemList.size() - 1);
        assertThat(testRecordItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecordItem.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRecordItem.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecordItem() throws Exception {
        int databaseSizeBeforeUpdate = recordItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordItemMockMvc.perform(put("/api/record-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecordItem() throws Exception {
        // Initialize the database
        recordItemService.save(recordItem);

        int databaseSizeBeforeDelete = recordItemRepository.findAll().size();

        // Delete the recordItem
        restRecordItemMockMvc.perform(delete("/api/record-items/{id}", recordItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
