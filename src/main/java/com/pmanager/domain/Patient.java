package com.pmanager.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * The Patient entity.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "re_enrollment")
    private Boolean reEnrollment;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "whatsapp")
    private String whatsapp;

    @Column(name = "cell_number")
    private String cellNumber;

    @Column(name = "emergency_number")
    private String emergencyNumber;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birth_date")
    private Instant birthDate;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @OneToMany(mappedBy = "patient")
    private Set<Record> records = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Turn> turns = new HashSet<>();

    public Patient() {}

    public Patient(Long id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patient firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isReEnrollment() {
        return reEnrollment;
    }

    public Patient reEnrollment(Boolean reEnrollment) {
        this.reEnrollment = reEnrollment;
        return this;
    }

    public void setReEnrollment(Boolean reEnrollment) {
        this.reEnrollment = reEnrollment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Patient phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public Patient whatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Patient cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public Patient emergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
        return this;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getAddress() {
        return address;
    }

    public Patient address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public Patient birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Patient createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Patient records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Patient addRecord(Record record) {
        this.records.add(record);
        record.setPatient(this);
        return this;
    }

    public Patient removeRecord(Record record) {
        this.records.remove(record);
        record.setPatient(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public Patient turns(Set<Turn> turns) {
        this.turns = turns;
        return this;
    }

    public Patient addTurn(Turn turn) {
        this.turns.add(turn);
        turn.setPatient(this);
        return this;
    }

    public Patient removeTurn(Turn turn) {
        this.turns.remove(turn);
        turn.setPatient(null);
        return this;
    }

    public void setTurns(Set<Turn> turns) {
        this.turns = turns;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", reEnrollment='" + isReEnrollment() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", cellNumber='" + getCellNumber() + "'" +
            ", emergencyNumber='" + getEmergencyNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
