package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.Order;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.REGISTERED;
import static com.hhovhann.photostudioservice.domain.data.OrderStatus.UNREGISTERED;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Long create(OrderRequestDTO orderRequestDTO) {
        Order order = orderMapper.toEntity(orderRequestDTO);
        if (Objects.isNull(orderRequestDTO.getLocalDateTime())) {
            order.setOrderStatus(UNREGISTERED);
        } else {
            // TODO check that date in range of working hours from 8 - 24.00
            order.setOrderStatus(REGISTERED);
        }
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public void update(Long orderId, LocalDateTime localDateTime) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        order.setLocalDateTime(Objects.requireNonNull(localDateTime));
        order.setOrderStatus(REGISTERED);
        orderRepository.save(order);
    }

    @Override
    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        orderRepository.delete(order);
    }

    @Override
    public void assign(Long orderId, Long photographerId) {
        // TODO find order by id, otherwise exception: orderRepository.findById(orderId)
        // TODO find photographer by id, otherwise exception photographerRepository.findById(photographerId)
        // TODO add photographer with entity util method: order.addPhotographer()
        // TODO save order: orderRepository.save(order)
    }

    @Override
    public void unassign(Long orderId, Long photographerId) {
        // TODO find order by id, otherwise exception: orderRepository.findById(orderId)
        // TODO find photographer by id, otherwise exception photographerRepository.findById(photographerId)
        // TODO remove photographer with entity util method: order.removePhotographer()
        // TODO save order: orderRepository.save(order)
    }



    @Override
    public void uploadPhoto(Long orderId, String photoUrl) {
        // TODO find photographer by id
        // TODO find order by photographer id
        // TODO Check with photo url that photo exists in web and that is zipped
        // TODO add photo link to order entity - probably we should have photoUrl field what was not in requirments
        // TODO save order: orderRepository.save(order)
    }
}
