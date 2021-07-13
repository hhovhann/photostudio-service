package com.hhovhann.photostudioservice.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderRequestDTO {
    @NotNull(message = "First Name may not be null")
    private String firstName;
    @NotNull(message = "Last Name may not be null")
    private String lastName;
    @NotNull(message = "Email may not be null")
    private String email;
    @NotNull(message = "Phone may not be null")
    private String phone;
    @NotNull(message = "Order Type may not be null")
    private String orderType;

    private LocalDateTime localDateTime;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
