package com.pmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RecordItem.
 */
@Entity
@Table(name = "record_item")
public class RecordItem implements Serializable {
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

    @OneToMany(mappedBy = "recordItem")
    private Set<Prescription> presciptions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Record record;

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

    public RecordItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public RecordItem createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public RecordItem lastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Prescription> getPresciptions() {
        return presciptions;
    }

    public RecordItem presciptions(Set<Prescription> prescriptions) {
        this.presciptions = prescriptions;
        return this;
    }

    public RecordItem addPresciption(Prescription prescription) {
        this.presciptions.add(prescription);
        prescription.setRecordItem(this);
        return this;
    }

    public RecordItem removePresciption(Prescription prescription) {
        this.presciptions.remove(prescription);
        prescription.setRecordItem(null);
        return this;
    }

    public void setPresciptions(Set<Prescription> prescriptions) {
        this.presciptions = prescriptions;
    }

    public Record getRecord() {
        return record;
    }

    public RecordItem record(Record record) {
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
        if (!(o instanceof RecordItem)) {
            return false;
        }
        return id != null && id.equals(((RecordItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecordItem{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            ", record='" + getRecord() + "'" +
            "}";
    }
}
