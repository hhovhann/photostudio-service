package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.stub.OrderStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.PENDING;
import static com.hhovhann.photostudioservice.domain.data.OrderStatus.UNSCHEDULED;
import static com.hhovhann.photostudioservice.domain.data.PhotoType.EVENTS;
import static com.hhovhann.photostudioservice.domain.data.PhotoType.REAL_ESTATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderMapper orderMapper;
    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.of(2021, 03, 03, 11, 0, 0, 0, ZoneId.of("UTC"));

    @Test
    @DisplayName("Should Create Order with date time")
    void shouldSuccessfullyCreateOrderWithDataTime() {
        // given
        OrderRequestDTO orderRequestDTO = OrderStub.createOrderRequestDto(1, REAL_ESTATE, ZONED_DATE_TIME);
        OrderEntity orderEntity = OrderStub.createOrderEntity(1, REAL_ESTATE, PENDING);
        List<OrderEntity> orderEntities = Collections.singletonList(orderEntity);
        when(orderMapper.toEntity(orderRequestDTO)).thenReturn(orderEntity);
        when(orderRepository.saveAll(orderEntities)).thenReturn(orderEntities);
        // when
        List<Long> orderEntityIds = orderService.create(Collections.singletonList(orderRequestDTO));
        // then
        assertEquals(orderEntityIds.size(), 1);
        assertEquals(orderEntityIds.get(0), 1L);
    }

    @Test
    @DisplayName("Should Create Order without date time")
    void shouldSuccessfullyCreateOrderWithoutDataTime() {
        // given
        OrderRequestDTO orderRequestDTO = OrderStub.createOrderRequestDto(1, EVENTS, ZONED_DATE_TIME);
        OrderEntity orderEntity = OrderStub.createOrderEntity(1, EVENTS, UNSCHEDULED);
        List<OrderEntity> orderEntities = Collections.singletonList(orderEntity);
        when(orderMapper.toEntity(orderRequestDTO)).thenReturn(orderEntity);
        when(orderRepository.saveAll(orderEntities)).thenReturn(orderEntities);
        // when
        List<Long> orderEntityIds = orderService.create(Collections.singletonList(orderRequestDTO));
        // then
        assertEquals(orderEntityIds.size(), 1);
        assertEquals(orderEntityIds.get(0), 1L);
    }

    @Test
    @DisplayName("Should Update Order with date time")
    void shouldSuccessfullyUpdateOrderWithDataTime() {
        // given
        OrderEntity orderEntity = OrderStub.createOrderEntityWithoutDateTime(1, EVENTS);
        OrderResponseDTO orderResponseDTO = OrderStub.createOrderResponseDTO(1, EVENTS, PENDING, ZONED_DATE_TIME);
        when(orderMapper.toDTO(orderEntity)).thenReturn(orderResponseDTO);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        // when
        OrderResponseDTO updatedOrderEntity = orderService.update(1L, ZONED_DATE_TIME);
        // then
        assertEquals(updatedOrderEntity.getOrderStatus(), PENDING);
        assertEquals(updatedOrderEntity.getCreationDateTime(), ZONED_DATE_TIME);
    }

    @Test
    @DisplayName("Should assign Order to the photographer")
    void shouldSuccessfullyAssignOrderToPhotographer() {
        OrderEntity orderEntity = OrderStub.createOrderEntityWithoutDateTime(1, EVENTS);
        OrderResponseDTO orderResponseDTO = OrderStub.createOrderResponseDTO(1, EVENTS, PENDING, ZONED_DATE_TIME);

        orderService.assign(1L, 1L);
    }

    @Test
    @DisplayName("Should upload the zip file")
    void shouldSuccessfullyUploadImageToOrder() {
    }

    @Test
    @DisplayName("Should verify file content")
    void shouldSuccessfullyVerifyImageContent() {
    }

    @Test
    @DisplayName("Should cancel the order and remove from database")
    void shouldSuccessfullyCancelOrder() {
    }
}