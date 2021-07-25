package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
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

    public OrderResponseDTO toDTO(OrderEntity updatedEntity) {
        ContactData contactData = updatedEntity.getContactData();

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setName(contactData.getName());
        orderResponseDTO.setSurname(contactData.getSurname());
        orderResponseDTO.setCellNumber(contactData.getCellNumber());
        orderResponseDTO.setEmail(contactData.getEmail());

        orderResponseDTO.setId(updatedEntity.getId());
        orderResponseDTO.setPhotoType(updatedEntity.getPhotoType());
        orderResponseDTO.setOrderStatus(updatedEntity.getOrderStatus());
        orderResponseDTO.setTitle(updatedEntity.getTitle());
        orderResponseDTO.setLogisticInfo(updatedEntity.getLogisticInfo());
        orderResponseDTO.setCreationDateTime(updatedEntity.getCreationDateTime());
        orderResponseDTO.setPhotographerEntities(updatedEntity.getPhotographers());
        return orderResponseDTO;
    }
}
