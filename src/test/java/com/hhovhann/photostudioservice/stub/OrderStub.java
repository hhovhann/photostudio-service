package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import com.hhovhann.photostudioservice.domain.data.PhotoType;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import java.time.ZonedDateTime;

public class OrderStub {

    public static OrderEntity createOrderEntity(long id, PhotoType photoType, OrderStatus orderStatus) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setPhotoType(photoType);
        orderEntity.setOrderStatus(orderStatus);
        orderEntity.setContactData(ContactDataStub.createOrderRequestDto(id, photoType));

        return orderEntity;
    }

    public static OrderEntity createOrderEntityWithoutDateTime(long id, PhotoType photoType) {
        OrderEntity orderEntity = new OrderEntity();

        ContactData contactData = new ContactData();
        contactData.setName("Order name " + id);
        contactData.setSurname("Order surname");
        contactData.setEmail("order" + id + "@email.com");
        contactData.setCellNumber("01234567890");

        orderEntity.setId(id);
        orderEntity.setContactData(contactData);
        orderEntity.setTitle("Order logistic title" + id);
        orderEntity.setLogisticInfo("Order logistic info" + id);
        orderEntity.setPhotoType(photoType);

        return orderEntity;
    }

    public static OrderRequestDTO createOrderRequestDto(long sequence, PhotoType photoType, ZonedDateTime createDateTime) {
        OrderRequestDTO orderRequestDTO = createOrderRequestDtoWithoutDateTime(sequence, photoType);
        orderRequestDTO.setLocalDateTime(createDateTime);

        return orderRequestDTO;
    }

    public static OrderRequestDTO createOrderRequestDtoWithoutDateTime(long sequence, PhotoType photoType) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setName("Order name " + sequence);
        orderRequestDTO.setSurname("Order surname");
        orderRequestDTO.setEmail("order" + sequence + "@email.com");
        orderRequestDTO.setCellNumber("01234567890");
        orderRequestDTO.setTitle("Order logistic title" + sequence);
        orderRequestDTO.setLogisticInfo("Order logistic info" + sequence);
        orderRequestDTO.setPhotoType(photoType);

        return orderRequestDTO;
    }

    public static OrderResponseDTO createOrderResponseDTO(int id, PhotoType photoType, OrderStatus orderStatus, ZonedDateTime createDateTime) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setName("Order name " + id);
        orderResponseDTO.setSurname("Order surname");
        orderResponseDTO.setEmail("order" + id + "@email.com");
        orderResponseDTO.setCellNumber("01234567890");
        orderResponseDTO.setTitle("Order logistic title" + id);
        orderResponseDTO.setLogisticInfo("Order logistic info" + id);
        orderResponseDTO.setOrderStatus(orderStatus);
        orderResponseDTO.setPhotoType(photoType);
        orderResponseDTO.setCreationDateTime(createDateTime);

        return orderResponseDTO;
    }
}
