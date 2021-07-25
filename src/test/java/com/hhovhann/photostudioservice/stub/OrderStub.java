package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import com.hhovhann.photostudioservice.domain.data.PhotoType;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;

import static java.time.ZonedDateTime.now;

public class OrderStub {

    public static OrderEntity createOrderEntity(long id, PhotoType photoType, OrderStatus orderStatus) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setPhotoType(photoType);
        orderEntity.setOrderStatus(orderStatus);
        orderEntity.setContactData(ContactDataStub.createOrderRequestDto(id, photoType));

        return orderEntity;
    }

    public static OrderRequestDTO createOrderRequestDto(long sequence, PhotoType photoType) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setName("Order name " + sequence);
        orderRequestDTO.setSurname("Order surname");
        orderRequestDTO.setEmail("order" + sequence + "@email.com");
        orderRequestDTO.setCellNumber("01234567890");
        orderRequestDTO.setTitle("Order logistic title" + sequence);
        orderRequestDTO.setLogisticInfo("Order logistic info" + sequence);
        orderRequestDTO.setPhotoType(photoType);
        orderRequestDTO.setLocalDateTime(now());

        return orderRequestDTO;
    }
}
