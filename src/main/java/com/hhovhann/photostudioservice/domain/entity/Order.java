package com.hhovhann.photostudioservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import com.hhovhann.photostudioservice.domain.data.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "order_seq")
    @SequenceGenerator(allocationSize = 100, name = "order_seq", sequenceName = "order_sequence")
    private Long id;

    @Embedded
    private ContactData contactData;

    @Enumerated(STRING)
    private OrderType orderType;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Photographer> photographers = new ArrayList<>();

    private String title;

    private LocalDateTime localDateTime;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public List<Photographer> getPhotographers() {
        return photographers;
    }

    public void setPhotographers(List<Photographer> photographers) {
        this.photographers = photographers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void addPhotographer(Photographer photographer) {
        photographers.add(photographer);
        photographer.setOrder(this);
    }

    public void removePhotographer(Photographer photographer) {
        photographers.remove(photographer);
        photographer.setOrder(null);
    }
}
