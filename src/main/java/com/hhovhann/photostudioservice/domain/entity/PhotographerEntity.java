package com.hhovhann.photostudioservice.domain.entity;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class PhotographerEntity implements Serializable {

    private static final long serialVersionUID = 5969606383306911255L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "photographer_seq")
    @SequenceGenerator(allocationSize = 70, name = "photographer_seq", sequenceName = "photographer_sequence")
    private Long id;

    @Embedded
    @NotNull
    private ContactData contactData;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_photographer_order_id"))
    private OrderEntity orderEntity;

    public PhotographerEntity() {
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

    public OrderEntity getOrder() {
        return orderEntity;
    }

    public void setOrder(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotographerEntity)) return false;
        return id != null && id.equals(((PhotographerEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
