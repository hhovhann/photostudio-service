package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.entity.Order;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;


    public OrderResponseDTO toDto(Order order) {
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    public Order toEntity(OrderRequestDTO orderRequestDTO) {
        return modelMapper.map(orderRequestDTO, Order.class);
    }
}
