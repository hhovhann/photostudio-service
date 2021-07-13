package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.Order;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    /***
     * Find all stored orders
     * @return list of orders
     */
    List<Order> findAll();

    /***
     * Create order with or without local date time
     * @param orderRequestDTO - requested domain object to persist
     * @return created order instance id
     */
    Long create(OrderRequestDTO orderRequestDTO);

    /***
     * Modify created order
     * @param orderId - order instance id
     * @param localDateTime - local date time working hours 08.00 - 24.00
     */
    void update(Long orderId, LocalDateTime localDateTime);

    /***
     * Remove order from database if exists
     * @param orderId -  order instance id
     */
    void cancel(Long orderId);

    /***
     * Assign order to photographer
     * @param orderId -  order instance id
     * @param photographerId -  photographer instance id
     */
    void assign(Long orderId, Long photographerId);
    /***
     * Un Assign order from photographer
     * @param orderId -  order instance id
     * @param photographerId -  photographer instance id
     */
    void unassign(Long orderId, Long photographerId);
    /***
     * Upload photo to order
     * @param orderId - order instance id
     */
    void uploadPhoto(Long orderId, String photoUrl);
}
