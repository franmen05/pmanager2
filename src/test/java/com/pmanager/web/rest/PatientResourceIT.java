package com.pmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pmanager.PmanagerApp;
import com.pmanager.domain.Patient;
import com.pmanager.repository.PatientRepository;
import com.pmanager.service.PatientService;
import com.pmanager.service.dto.PatientDTO;
import com.pmanager.service.mapper.PatientMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@SpringBootTest(classes = PmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PatientResourceIT {
    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RE_ENROLLMENT = false;
    private static final Boolean UPDATED_RE_ENROLLMENT = true;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WHATSAPP = "AAAAAAAAAA";
    private static final String UPDATED_WHATSAPP = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CELL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .whatsapp(DEFAULT_WHATSAPP)
            .cellNumber(DEFAULT_CELL_NUMBER)
            .emergencyNumber(DEFAULT_EMERGENCY_NUMBER)
            .address(DEFAULT_ADDRESS)
            .birthDate(DEFAULT_BIRTH_DATE)
            .createDate(DEFAULT_CREATE_DATE);
        return patient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .whatsapp(UPDATED_WHATSAPP)
            .cellNumber(UPDATED_CELL_NUMBER)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .address(UPDATED_ADDRESS)
            .birthDate(UPDATED_BIRTH_DATE)
            .createDate(UPDATED_CREATE_DATE);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);
        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPatient.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testPatient.getCellNumber()).isEqualTo(DEFAULT_CELL_NUMBER);
        assertThat(testPatient.getEmergencyNumber()).isEqualTo(DEFAULT_EMERGENCY_NUMBER);
        assertThat(testPatient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatient.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPatient.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setFirstName(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setLastName(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setAddress(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setCreateDate(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc
            .perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc
            .perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].reEnrollment").value(hasItem(DEFAULT_RE_ENROLLMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].cellNumber").value(hasItem(DEFAULT_CELL_NUMBER)))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc
            .perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.reEnrollment").value(DEFAULT_RE_ENROLLMENT.booleanValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP))
            .andExpect(jsonPath("$.cellNumber").value(DEFAULT_CELL_NUMBER))
            .andExpect(jsonPath("$.emergencyNumber").value(DEFAULT_EMERGENCY_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .whatsapp(UPDATED_WHATSAPP)
            .cellNumber(UPDATED_CELL_NUMBER)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .address(UPDATED_ADDRESS)
            .birthDate(UPDATED_BIRTH_DATE)
            .createDate(UPDATED_CREATE_DATE);
        PatientDTO patientDTO = patientMapper.toDto(updatedPatient);

        restPatientMockMvc
            .perform(put("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPatient.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testPatient.getCellNumber()).isEqualTo(UPDATED_CELL_NUMBER);
        assertThat(testPatient.getEmergencyNumber()).isEqualTo(UPDATED_EMERGENCY_NUMBER);
        assertThat(testPatient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatient.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPatient.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(put("/api/patients").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc
            .perform(delete("/api/patients/{id}", patient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
