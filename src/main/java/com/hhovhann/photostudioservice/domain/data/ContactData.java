package com.hhovhann.photostudioservice.domain.data;

import javax.persistence.Embeddable;

@Embeddable
public class ContactData {
    private String name;

    private String surname;

    private String email;

    private String cellNumber;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String lastName) {
        this.surname = lastName;
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

    public void setCellNumber(String phone) {
        this.cellNumber = phone;
    }
}
