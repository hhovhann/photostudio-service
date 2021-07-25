package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import com.hhovhann.photostudioservice.stub.OrderStub;
import com.hhovhann.photostudioservice.stub.PhotographerStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.*;
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
    PhotographerRepository  photographerRepository;
    @MockBean
    OrderMapper orderMapper;

    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.of(2021, 03, 03, 11, 0, 0, 0, ZoneId.of("UTC"));

    @Test
    @DisplayName("Should Create Order with date time")
    void shouldSuccessfullyCreateOrderWithDataTime() {
        OrderRequestDTO orderRequestDTO = OrderStub.createOrderRequestDto(1, REAL_ESTATE, ZONED_DATE_TIME);
        OrderEntity orderEntity = OrderStub.createOrderEntity(1, REAL_ESTATE, PENDING);
        List<OrderEntity> orderEntities = Collections.singletonList(orderEntity);
        when(orderMapper.toEntity(orderRequestDTO)).thenReturn(orderEntity);
        when(orderRepository.saveAll(orderEntities)).thenReturn(orderEntities);

        List<Long> orderEntityIds = orderService.create(Collections.singletonList(orderRequestDTO));

        assertEquals(orderEntityIds.size(), 1);
        assertEquals(orderEntityIds.get(0), 1L);
    }

    @Test
    @DisplayName("Should Create Order without date time")
    void shouldSuccessfullyCreateOrderWithoutDataTime() {
        OrderRequestDTO orderRequestDTO = OrderStub.createOrderRequestDto(1, EVENTS, ZONED_DATE_TIME);
        OrderEntity orderEntity = OrderStub.createOrderEntity(1, EVENTS, UNSCHEDULED);
        List<OrderEntity> orderEntities = Collections.singletonList(orderEntity);
        when(orderMapper.toEntity(orderRequestDTO)).thenReturn(orderEntity);
        when(orderRepository.saveAll(orderEntities)).thenReturn(orderEntities);

        List<Long> orderEntityIds = orderService.create(Collections.singletonList(orderRequestDTO));

        assertEquals(orderEntityIds.size(), 1);
        assertEquals(orderEntityIds.get(0), 1L);
    }

    @Test
    @DisplayName("Should Update Order with date time")
    void shouldSuccessfullyUpdateOrderWithDataTime() {
        OrderEntity orderEntity = OrderStub.createOrderEntityWithoutDateTime(1, EVENTS);
        OrderResponseDTO orderResponseDTO = OrderStub.createOrderResponseDTO(1, EVENTS, PENDING, ZONED_DATE_TIME);
        when(orderMapper.toDTO(orderEntity)).thenReturn(orderResponseDTO);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        OrderResponseDTO updatedOrderEntity = orderService.update(1L, ZONED_DATE_TIME);

        assertEquals(updatedOrderEntity.getOrderStatus(), PENDING);
        assertEquals(updatedOrderEntity.getCreationDateTime(), ZONED_DATE_TIME);
    }

    @Test
    @DisplayName("Should assign Order to the photographer")
    void shouldSuccessfullyAssignOrderToPhotographer() {
        PhotographerEntity photographerEntity = PhotographerStub.createPhotographerEntity(1);
        OrderEntity orderEntity = OrderStub.createOrderEntityWithoutDateTime(1, EVENTS);
        orderEntity.addPhotographer(photographerEntity);
        orderEntity.setOrderStatus(PENDING);
        OrderResponseDTO orderResponseDTO = OrderStub.createOrderResponseDTO(1, EVENTS, ASSIGNED, ZONED_DATE_TIME);
        orderResponseDTO.setPhotographerEntities(Collections.singletonList(photographerEntity));
        when(orderRepository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));
        when(photographerRepository.findById(1L)).thenReturn(Optional.of(photographerEntity));
        when(orderMapper.toDTO(orderEntity)).thenReturn(orderResponseDTO);

        orderService.assign(orderEntity.getId(), photographerEntity.getId());

        assertEquals(orderResponseDTO.getOrderStatus(), ASSIGNED);
        assertEquals(orderResponseDTO.getPhotographerEntities().size(), 1);
    }

    @Test
    @DisplayName("Should upload the zip file")
    void shouldSuccessfullyUploadImageToOrder() {
        MockMultipartFile zipFIle1
                = new MockMultipartFile(
                "file",
                "hello.zip",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Hello, World!".getBytes()
        );
        MockMultipartFile zipFIle = new MockMultipartFile("jacoco-report.zip", "content".getBytes());

        OrderResponseDTO orderResponseDTO = orderService.uploadPhoto(1L, zipFIle);

        assertEquals(orderResponseDTO.getOrderStatus(), UPLOADED);

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