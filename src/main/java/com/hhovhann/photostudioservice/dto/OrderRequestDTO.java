package com.hhovhann.photostudioservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhovhann.photostudioservice.domain.data.PhotoType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class OrderRequestDTO {
    @NotBlank(message = "Name may not be null")
    private String name;
    @NotBlank(message = "Surname may not be null")
    private String surname;
    @Email
    @NotBlank(message = "Email may not be null")
    private String email;
    @NotBlank(message = "Cell number may not be null")
    @JsonProperty("cell_number")
    private String cellNumber;
    @NotNull(message = "Photo Type may not be null")
    @Enumerated(EnumType.STRING)
    @JsonProperty("photo_type")
    private PhotoType photoType;

    private String title;

    private String logisticInfo;

    private ZonedDateTime localDateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public ZonedDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(ZonedDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }
}
