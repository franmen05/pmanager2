package com.pmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MedicalHistory.
 */
@Entity
@Table(name = "medical_history")
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "create_date")
    private Instant createDate;

    @NotNull
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    @OneToMany(mappedBy = "medicalHistory")
    private Set<MedicalHistoryConfig> medicalHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    private Record record;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public MedicalHistory comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public MedicalHistory createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public MedicalHistory lastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<MedicalHistoryConfig> getMedicalHistories() {
        return medicalHistories;
    }

    public MedicalHistory medicalHistories(Set<MedicalHistoryConfig> medicalHistoryConfigs) {
        this.medicalHistories = medicalHistoryConfigs;
        return this;
    }

    public MedicalHistory addMedicalHistory(MedicalHistoryConfig medicalHistoryConfig) {
        this.medicalHistories.add(medicalHistoryConfig);
        medicalHistoryConfig.setMedicalHistory(this);
        return this;
    }

    public MedicalHistory removeMedicalHistory(MedicalHistoryConfig medicalHistoryConfig) {
        this.medicalHistories.remove(medicalHistoryConfig);
        medicalHistoryConfig.setMedicalHistory(null);
        return this;
    }

    public void setMedicalHistories(Set<MedicalHistoryConfig> medicalHistoryConfigs) {
        this.medicalHistories = medicalHistoryConfigs;
    }

    public Record getRecord() {
        return record;
    }

    public MedicalHistory record(Record record) {
        this.record = record;
        return this;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalHistory)) {
            return false;
        }
        return id != null && id.equals(((MedicalHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalHistory{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            "}";
    }
}
