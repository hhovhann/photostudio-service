package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.Order;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import org.modelmapper.ModelMapper;

public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toEntity(OrderResponseDTO orderResponseDTO) {
        return modelMapper.map(orderResponseDTO, Order.class);
    }

    public Order toEntity(OrderRequestDTO orderRequestDTO) {
        return modelMapper.map(orderRequestDTO, Order.class);
    }
}
