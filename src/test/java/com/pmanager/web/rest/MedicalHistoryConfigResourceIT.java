package com.pmanager.web.rest;

import com.pmanager.PmanagerApp;
import com.pmanager.domain.MedicalHistoryConfig;
import com.pmanager.repository.MedicalHistoryConfigRepository;
import com.pmanager.service.MedicalHistoryConfigService;

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
 * Integration tests for the {@link MedicalHistoryConfigResource} REST controller.
 */
@SpringBootTest(classes = PmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalHistoryConfigResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MedicalHistoryConfigRepository medicalHistoryConfigRepository;

    @Autowired
    private MedicalHistoryConfigService medicalHistoryConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalHistoryConfigMockMvc;

    private MedicalHistoryConfig medicalHistoryConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalHistoryConfig createEntity(EntityManager em) {
        MedicalHistoryConfig medicalHistoryConfig = new MedicalHistoryConfig()
            .key(DEFAULT_KEY)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE);
        return medicalHistoryConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalHistoryConfig createUpdatedEntity(EntityManager em) {
        MedicalHistoryConfig medicalHistoryConfig = new MedicalHistoryConfig()
            .key(UPDATED_KEY)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        return medicalHistoryConfig;
    }

    @BeforeEach
    public void initTest() {
        medicalHistoryConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalHistoryConfig() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryConfigRepository.findAll().size();
        // Create the MedicalHistoryConfig
        restMedicalHistoryConfigMockMvc.perform(post("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistoryConfig in the database
        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalHistoryConfig testMedicalHistoryConfig = medicalHistoryConfigList.get(medicalHistoryConfigList.size() - 1);
        assertThat(testMedicalHistoryConfig.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMedicalHistoryConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMedicalHistoryConfig.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testMedicalHistoryConfig.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createMedicalHistoryConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryConfigRepository.findAll().size();

        // Create the MedicalHistoryConfig with an existing ID
        medicalHistoryConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalHistoryConfigMockMvc.perform(post("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistoryConfig in the database
        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryConfigRepository.findAll().size();
        // set the field null
        medicalHistoryConfig.setKey(null);

        // Create the MedicalHistoryConfig, which fails.


        restMedicalHistoryConfigMockMvc.perform(post("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isBadRequest());

        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryConfigRepository.findAll().size();
        // set the field null
        medicalHistoryConfig.setDescription(null);

        // Create the MedicalHistoryConfig, which fails.


        restMedicalHistoryConfigMockMvc.perform(post("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isBadRequest());

        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalHistoryConfigRepository.findAll().size();
        // set the field null
        medicalHistoryConfig.setLastUpdateDate(null);

        // Create the MedicalHistoryConfig, which fails.


        restMedicalHistoryConfigMockMvc.perform(post("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isBadRequest());

        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalHistoryConfigs() throws Exception {
        // Initialize the database
        medicalHistoryConfigRepository.saveAndFlush(medicalHistoryConfig);

        // Get all the medicalHistoryConfigList
        restMedicalHistoryConfigMockMvc.perform(get("/api/medical-history-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalHistoryConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicalHistoryConfig() throws Exception {
        // Initialize the database
        medicalHistoryConfigRepository.saveAndFlush(medicalHistoryConfig);

        // Get the medicalHistoryConfig
        restMedicalHistoryConfigMockMvc.perform(get("/api/medical-history-configs/{id}", medicalHistoryConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalHistoryConfig.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(DEFAULT_LAST_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMedicalHistoryConfig() throws Exception {
        // Get the medicalHistoryConfig
        restMedicalHistoryConfigMockMvc.perform(get("/api/medical-history-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalHistoryConfig() throws Exception {
        // Initialize the database
        medicalHistoryConfigService.save(medicalHistoryConfig);

        int databaseSizeBeforeUpdate = medicalHistoryConfigRepository.findAll().size();

        // Update the medicalHistoryConfig
        MedicalHistoryConfig updatedMedicalHistoryConfig = medicalHistoryConfigRepository.findById(medicalHistoryConfig.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalHistoryConfig are not directly saved in db
        em.detach(updatedMedicalHistoryConfig);
        updatedMedicalHistoryConfig
            .key(UPDATED_KEY)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restMedicalHistoryConfigMockMvc.perform(put("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalHistoryConfig)))
            .andExpect(status().isOk());

        // Validate the MedicalHistoryConfig in the database
        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeUpdate);
        MedicalHistoryConfig testMedicalHistoryConfig = medicalHistoryConfigList.get(medicalHistoryConfigList.size() - 1);
        assertThat(testMedicalHistoryConfig.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMedicalHistoryConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMedicalHistoryConfig.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testMedicalHistoryConfig.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalHistoryConfig() throws Exception {
        int databaseSizeBeforeUpdate = medicalHistoryConfigRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalHistoryConfigMockMvc.perform(put("/api/medical-history-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryConfig)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistoryConfig in the database
        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalHistoryConfig() throws Exception {
        // Initialize the database
        medicalHistoryConfigService.save(medicalHistoryConfig);

        int databaseSizeBeforeDelete = medicalHistoryConfigRepository.findAll().size();

        // Delete the medicalHistoryConfig
        restMedicalHistoryConfigMockMvc.perform(delete("/api/medical-history-configs/{id}", medicalHistoryConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalHistoryConfig> medicalHistoryConfigList = medicalHistoryConfigRepository.findAll();
        assertThat(medicalHistoryConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
