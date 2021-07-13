package com.hhovhann.photostudioservice.dto;

import com.hhovhann.photostudioservice.domain.data.PhotoType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PhotographerRequestDTO {
    @NotNull(message = "First Name may not be null")
    private String firstName;
    @NotNull(message = "Last Name may not be null")
    private String lastName;
    @NotNull(message = "Email may not be null")
    private String email;
    @NotNull(message = "Phone may not be null")
    private String phone;
    @NotNull(message = "Order Type may not be null")

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
