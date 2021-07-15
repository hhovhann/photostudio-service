package com.hhovhann.photostudioservice.domain.data;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Embeddable
public class ContactData {
    private String name;

    private String surname;
    @Email
    private String email;

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
