package com.pmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A MedicalHistoryConfig.
 */
@Entity
@Table(name = "medical_history_config")
public class MedicalHistoryConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    //    @Getter
    //    @Setter
    //    @NotNull
    @Column(name = "jhi_type", nullable = true)
    private String type;

    @Column(name = "create_date")
    private Instant createDate;

    @NotNull
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    //
    //    @ManyToOne
    //    @JsonIgnoreProperties(value = "medicalHistories", allowSetters = true)
    //    private MedicalHistory medicalHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MedicalHistoryConfig key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public MedicalHistoryConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public MedicalHistoryConfig createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public MedicalHistoryConfig lastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    //    public MedicalHistory getMedicalHistory() {
    //        return medicalHistory;
    //    }
    //
    //    public MedicalHistoryConfig medicalHistory(MedicalHistory medicalHistory) {
    //        this.medicalHistory = medicalHistory;
    //        return this;
    //    }
    //    public void setMedicalHistory(MedicalHistory medicalHistory) {
    //        this.medicalHistory = medicalHistory;
    //    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalHistoryConfig)) {
            return false;
        }
        return id != null && id.equals(((MedicalHistoryConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalHistoryConfig{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            "}";
    }
}
