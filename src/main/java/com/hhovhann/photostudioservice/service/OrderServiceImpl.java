package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.PENDING;
import static com.hhovhann.photostudioservice.domain.data.OrderStatus.UNSCHEDULED;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Long create(OrderRequestDTO orderRequestDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderRequestDTO);
        if (Objects.isNull(orderRequestDTO.getLocalDateTime())) {
            orderEntity.setOrderStatus(UNSCHEDULED);

        } else {
            // Validate local date time
            // TODO check hours 8 >= orderRequestDTO.getLocalDateTime().getHour() <= 24
            //  TODO check minutes 00 >= orderRequestDTO.getLocalDateTime().getMinute() <= 59
            // TODO check that date in range of working hours from 8 - 20.00
            orderEntity.setOrderStatus(PENDING);
        }
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    @Override
    @Transactional
    public void update(Long orderId, LocalDateTime localDateTime) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        orderEntity.setLocalDateTime(Objects.requireNonNull(localDateTime));
        orderEntity.setOrderStatus(PENDING);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void cancel(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        orderRepository.delete(orderEntity);
    }

    @Override
    @Transactional
    public void assign(Long orderId, Long photographerId) {
        // TODO find order by id, otherwise exception: orderRepository.findById(orderId)
        // TODO find photographer by id, otherwise exception photographerRepository.findById(photographerId)
        // TODO add photographer with entity util method: order.addPhotographer()
        // TODO save order: orderRepository.save(order)
    }

    @Override
    @Transactional
    public void unassign(Long orderId, Long photographerId) {
        // TODO find order by id, otherwise exception: orderRepository.findById(orderId)
        // TODO find photographer by id, otherwise exception photographerRepository.findById(photographerId)
        // TODO remove photographer with entity util method: order.removePhotographer()
        // TODO save order: orderRepository.save(order)
    }


    @Override
    @Transactional
    public void uploadPhoto(Long orderId, String photoUrl) {
        // example in AWS can check the image existence in WEB
        // TODO find photographer by id
        // TODO find order by photographer id
        // TODO Check with photo url that photo exists in web and that is zipped
        // TODO add photo link to order entity - probably we should have photoUrl field what was not in requirments
        // TODO save order: orderRepository.save(order)
    }
}
