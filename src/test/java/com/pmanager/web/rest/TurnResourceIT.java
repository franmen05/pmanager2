package com.pmanager.web.rest;

import com.pmanager.PmanagerApp;
import com.pmanager.domain.Turn;
import com.pmanager.repository.TurnRepository;
import com.pmanager.service.TurnService;

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

import com.pmanager.domain.enumeration.Status;
/**
 * Integration tests for the {@link TurnResource} REST controller.
 */
@SpringBootTest(classes = PmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TurnResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.REGISTER;
    private static final Status UPDATED_STATUS = Status.FINSHED;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private TurnService turnService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTurnMockMvc;

    private Turn turn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turn createEntity(EntityManager em) {
        Turn turn = new Turn()
            .position(DEFAULT_POSITION)
            .createDate(DEFAULT_CREATE_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .status(DEFAULT_STATUS);
        return turn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turn createUpdatedEntity(EntityManager em) {
        Turn turn = new Turn()
            .position(UPDATED_POSITION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .status(UPDATED_STATUS);
        return turn;
    }

    @BeforeEach
    public void initTest() {
        turn = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurn() throws Exception {
        int databaseSizeBeforeCreate = turnRepository.findAll().size();
        // Create the Turn
        restTurnMockMvc.perform(post("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isCreated());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeCreate + 1);
        Turn testTurn = turnList.get(turnList.size() - 1);
        assertThat(testTurn.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testTurn.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTurn.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testTurn.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTurnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnRepository.findAll().size();

        // Create the Turn with an existing ID
        turn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnMockMvc.perform(post("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isBadRequest());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnRepository.findAll().size();
        // set the field null
        turn.setPosition(null);

        // Create the Turn, which fails.


        restTurnMockMvc.perform(post("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isBadRequest());

        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnRepository.findAll().size();
        // set the field null
        turn.setLastUpdateDate(null);

        // Create the Turn, which fails.


        restTurnMockMvc.perform(post("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isBadRequest());

        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnRepository.findAll().size();
        // set the field null
        turn.setStatus(null);

        // Create the Turn, which fails.


        restTurnMockMvc.perform(post("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isBadRequest());

        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTurns() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        // Get all the turnList
        restTurnMockMvc.perform(get("/api/turns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turn.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTurn() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        // Get the turn
        restTurnMockMvc.perform(get("/api/turns/{id}", turn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(turn.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(DEFAULT_LAST_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTurn() throws Exception {
        // Get the turn
        restTurnMockMvc.perform(get("/api/turns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurn() throws Exception {
        // Initialize the database
        turnService.save(turn);

        int databaseSizeBeforeUpdate = turnRepository.findAll().size();

        // Update the turn
        Turn updatedTurn = turnRepository.findById(turn.getId()).get();
        // Disconnect from session so that the updates on updatedTurn are not directly saved in db
        em.detach(updatedTurn);
        updatedTurn
            .position(UPDATED_POSITION)
            .createDate(UPDATED_CREATE_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .status(UPDATED_STATUS);

        restTurnMockMvc.perform(put("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTurn)))
            .andExpect(status().isOk());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeUpdate);
        Turn testTurn = turnList.get(turnList.size() - 1);
        assertThat(testTurn.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testTurn.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTurn.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testTurn.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTurn() throws Exception {
        int databaseSizeBeforeUpdate = turnRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnMockMvc.perform(put("/api/turns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(turn)))
            .andExpect(status().isBadRequest());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurn() throws Exception {
        // Initialize the database
        turnService.save(turn);

        int databaseSizeBeforeDelete = turnRepository.findAll().size();

        // Delete the turn
        restTurnMockMvc.perform(delete("/api/turns/{id}", turn.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
