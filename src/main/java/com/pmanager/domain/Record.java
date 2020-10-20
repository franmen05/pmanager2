package com.pmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "create_date")
    private Instant createDate;

    @NotNull
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    @OneToMany(mappedBy = "record")
    private Set<Prescription> presciptions = new HashSet<>();

    @OneToMany(mappedBy = "record")
    private Set<MedicalHistory> medicalHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "records", allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Record description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Record createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Record lastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Prescription> getPresciptions() {
        return presciptions;
    }

    public Record presciptions(Set<Prescription> prescriptions) {
        this.presciptions = prescriptions;
        return this;
    }

    public Record addPresciption(Prescription prescription) {
        this.presciptions.add(prescription);
        prescription.setRecord(this);
        return this;
    }

    public Record removePresciption(Prescription prescription) {
        this.presciptions.remove(prescription);
        prescription.setRecord(null);
        return this;
    }

    public void setPresciptions(Set<Prescription> prescriptions) {
        this.presciptions = prescriptions;
    }

    public Set<MedicalHistory> getMedicalHistories() {
        return medicalHistories;
    }

    public Record medicalHistories(Set<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
        return this;
    }

    public Record addMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistories.add(medicalHistory);
        medicalHistory.setRecord(this);
        return this;
    }

    public Record removeMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistories.remove(medicalHistory);
        medicalHistory.setRecord(null);
        return this;
    }

    public void setMedicalHistories(Set<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }

    public Patient getPatient() {
        return patient;
    }

    public Record patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Record)) {
            return false;
        }
        return id != null && id.equals(((Record) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Record{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            "}";
    }
}
