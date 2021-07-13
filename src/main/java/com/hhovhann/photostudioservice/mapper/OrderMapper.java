package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.Order;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        ContactData contactData = new ContactData();
        contactData.setFirstName(orderRequestDTO.getFirstName());
        contactData.setLastName(orderRequestDTO.getLastName());
        contactData.setPhone(orderRequestDTO.getPhone());
        contactData.setEmail(orderRequestDTO.getEmail());
        order.setContactData(contactData);
        order.setOrderType(orderRequestDTO.getOrderType());
        order.setLocalDateTime(orderRequestDTO.getLocalDateTime());
        order.setTitle(orderRequestDTO.getTitle());
        return order;
    }
}
