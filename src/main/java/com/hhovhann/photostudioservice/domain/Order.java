package com.hhovhann.photostudioservice.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Entity
public class Order {


    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Embedded
    private ContactData contactData;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Photographer> photographers = new HashSet<>();

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

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<Photographer> getPhotographers() {
        return photographers;
    }

    public void setPhotographers(Set<Photographer> photographers) {
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
