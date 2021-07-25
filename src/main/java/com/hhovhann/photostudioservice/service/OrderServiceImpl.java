package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import com.hhovhann.photostudioservice.exception.OrderNotFoundException;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import com.hhovhann.photostudioservice.validatiors.DataValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DataValidator dataValidator;
    private final PhotographerRepository photographerRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, DataValidator dataValidator, OrderMapper orderMapper, PhotographerRepository photographerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.dataValidator = dataValidator;
        this.photographerRepository = photographerRepository;
    }

    @Override
    @Transactional
    public List<Long> create(List<OrderRequestDTO> orderRequestDTOs) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        for (OrderRequestDTO orderRequestDTO : orderRequestDTOs) {
            OrderEntity orderEntity = orderMapper.toEntity(orderRequestDTO);
            ZonedDateTime localDateTime = orderRequestDTO.getLocalDateTime();
            if (Objects.isNull(localDateTime)) {
                orderEntity.setOrderStatus(UNSCHEDULED);
            } else {
                dataValidator.validateBusinessHours(localDateTime);
                orderEntity.setOrderStatus(PENDING);
            }
            orderEntities.add(orderEntity);
        }
        orderRepository.saveAll(orderEntities);
        return orderEntities.stream().map(OrderEntity::getId).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO update(Long orderId, ZonedDateTime localDateTime) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with specified Id"));
        dataValidator.validateBusinessHours(localDateTime);
        orderEntity.setCreationDateTime(localDateTime);
        orderEntity.setOrderStatus(PENDING);
        return orderMapper.toDTO(orderRepository.save(orderEntity));
    }


    @Override
    @Transactional
    public void assign(Long orderId, Long photographerId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), PENDING);
        PhotographerEntity photographerEntity = photographerRepository.findById(photographerId).orElseThrow(() -> new OrderNotFoundException("No photographer found with specified Id"));
        orderEntity.addPhotographer(photographerEntity);
        orderEntity.setOrderStatus(ASSIGNED);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void uploadPhoto(Long orderId, MultipartFile zipFIle) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), ASSIGNED);
        dataValidator.validateFile(zipFIle);
        // TODO upload file to photo storage, take URL and store in database the image URL, now I just filename and store it in the database
        orderEntity.setImageUrl(zipFIle.getResource().getFilename());
        orderEntity.setOrderStatus(UPLOADED);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void verifyContent(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), UPLOADED);
        // now change the status to COMPLETED, in future logic should be added where real content verification will happen
        dataValidator.validatePhotoContent(orderEntity.getImageUrl());
        orderEntity.setOrderStatus(COMPLETED);
        orderRepository.save(orderEntity);
    }


    @Override
    @Transactional
    public void cancel(List<Long> orderIds) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        for (Long orderId : orderIds) {
            OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with specified Id"));
            orderEntities.add(orderEntity);
        }
        orderRepository.deleteAll(orderEntities);
    }
}
