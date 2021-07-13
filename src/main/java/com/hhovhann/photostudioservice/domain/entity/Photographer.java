package com.hhovhann.photostudioservice.domain.entity;


import com.hhovhann.photostudioservice.domain.data.ContactData;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photographer_seq")
    @SequenceGenerator(initialValue = 1, name = "photographer_seq", sequenceName = "photographer_sequence")
    private Long id;

    @Embedded
    private ContactData contactData;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_id"), name = "order_id")
    private Order order;

    public Photographer() {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photographer)) return false;
        return id != null && id.equals(((Photographer) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
