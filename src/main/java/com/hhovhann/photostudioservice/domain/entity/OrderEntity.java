package com.hhovhann.photostudioservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import com.hhovhann.photostudioservice.domain.data.PhotoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class OrderEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -2366519023138553485L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "order_seq")
    @SequenceGenerator(allocationSize = 90, name = "order_seq", sequenceName = "order_sequence")
    private Long id;

    @Embedded
    @NotNull
    private ContactData contactData;

    @Enumerated(STRING)
    @NotNull
    private PhotoType photoType;

    @Enumerated(STRING)
    @NotNull
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<PhotographerEntity> photographerEntities = new ArrayList<>();

    private String title;

    private String logisticInfo;

    private ZonedDateTime creationDateTime;

    private String imageUrl;

    public OrderEntity() {
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

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
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

    public List<PhotographerEntity> getPhotographers() {
        return photographerEntities;
    }

    public void setPhotographers(List<PhotographerEntity> photographerEntities) {
        this.photographerEntities = photographerEntities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(ZonedDateTime localDateTime) {
        this.creationDateTime = localDateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageURL) {
        this.imageUrl = imageURL;
    }

    public void addPhotographer(PhotographerEntity photographerEntity) {
        photographerEntities.add(photographerEntity);
        photographerEntity.setOrder(this);
    }

    public void removePhotographer(PhotographerEntity photographerEntity) {
        photographerEntities.remove(photographerEntity);
        photographerEntity.setOrder(null);
    }
}
