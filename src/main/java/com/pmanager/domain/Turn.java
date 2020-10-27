package com.pmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmanager.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Turn.
 */
@Entity
@Table(name = "turn")
public class Turn implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    //    @DateTimeFormat(pattern ="MM/DD/YY")
    @Column(name = "create_date")
    private Instant createDate;

    //    @NotNull
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    //    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "turns", allowSetters = true)
    private Patient patient;

    @PrePersist
    private void prePersistAction() {
        setLastUpdateDate(Instant.now());
    }

    public Turn create() {
        this.createDate = Instant.now();
        setStatus(Status.REGISTER);
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public Turn position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Turn createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Turn lastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    private void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Status getStatus() {
        return status;
    }

    public Turn status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Turn patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turn)) {
            return false;
        }
        return id != null && id.equals(((Turn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Turn{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", createDate='" + getCreateDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
