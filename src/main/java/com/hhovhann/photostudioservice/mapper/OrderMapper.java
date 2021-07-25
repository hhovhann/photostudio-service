package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import com.hhovhann.photostudioservice.dto.PhotographerResponseDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final PhotographerMapper photographerMapper;

    public OrderMapper(PhotographerMapper photographerMapper) {
        this.photographerMapper = photographerMapper;
    }

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
        List<PhotographerEntity> photographers = updatedEntity.getPhotographers();
        if (photographers.size() > 0) {
            List<PhotographerResponseDTO> photographerResponseDTOS = photographers.stream().map(photographerMapper::toResponseDTO).collect(Collectors.toList());
            orderResponseDTO.setPhotographers(photographerResponseDTOS);
        }
        return orderResponseDTO;
    }
}
