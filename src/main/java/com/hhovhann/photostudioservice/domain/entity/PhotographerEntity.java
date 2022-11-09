package com.hhovhann.photostudioservice.domain.entity;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
public class PhotographerEntity implements Serializable {

    @Serial
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

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
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
