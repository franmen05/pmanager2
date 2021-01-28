package com.pmanager.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmanager.domain.Record;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link com.pmanager.domain.Patient} entity.
 */
@ApiModel(description = "The Patient entity.")
public class PatientDTO implements Serializable {
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    private String phoneNumber;

    private String whatsapp;

    private String cellNumber;

    private String emergencyNumber;

    @NotNull
    private String address;

    private Instant birthDate;

    @NotNull
    private Instant createDate;

    //    @NotNull
    //    @Getter @Setter
    @JsonIgnoreProperties(value = "patient", allowSetters = true)
    private Set<Record> records;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        return id != null && id.equals(((PatientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", Records='" + getRecords() + "'" +
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
