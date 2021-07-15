package com.hhovhann.photostudioservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class PhotographerRequestDTO {
    @NotBlank(message = "Name may not be null")
    private String name;
    @NotBlank(message = "Surname may not be null")
    private String surname;
    @NotBlank(message = "Email may not be null")
    private String email;
    @NotBlank(message = "Cell number may not be null")
    @JsonProperty("cell_number")
    private String cellNumber;

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

}
