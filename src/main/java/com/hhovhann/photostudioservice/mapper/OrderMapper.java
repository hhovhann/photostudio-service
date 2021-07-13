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
        contactData.setFirstName(orderRequestDTO.getFirstName());
        contactData.setLastName(orderRequestDTO.getLastName());
        contactData.setPhone(orderRequestDTO.getPhone());
        contactData.setEmail(orderRequestDTO.getEmail());
        orderEntity.setContactData(contactData);
        orderEntity.setOrderType(orderRequestDTO.getOrderType());
        orderEntity.setLocalDateTime(orderRequestDTO.getLocalDateTime());
        orderEntity.setTitle(orderRequestDTO.getTitle());
        return orderEntity;
    }
}
