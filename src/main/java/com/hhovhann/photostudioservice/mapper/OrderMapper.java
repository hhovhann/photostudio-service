package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderRequestDTO orderRequestDTO) {
        OrderEntity orderEntity = new OrderEntity();

        ContactData contactData = new ContactData();
        contactData.setName(orderRequestDTO.getName());
        contactData.setSurname(orderRequestDTO.getSurname());
        contactData.setCellNumber(orderRequestDTO.getCellNumber());
        contactData.setEmail(orderRequestDTO.getEmail());

        orderEntity.setContactData(contactData);
        orderEntity.setPhotoType(orderRequestDTO.getPhotoType());
        orderEntity.setTitle(orderRequestDTO.getTitle());
        orderEntity.setLogisticInfo(orderRequestDTO.getLogisticInfo());
        orderEntity.setCreationDateTime(orderRequestDTO.getLocalDateTime());
        return orderEntity;
    }
}
