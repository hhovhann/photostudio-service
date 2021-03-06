package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import java.time.ZonedDateTime;
import java.util.List;

public interface OrderService {

    /***
     * Create order with or without local date time
     * @param orderRequestDTOs - list of requested domain objects to persist
     * @return created order instance id
     */
    List<Long> create(List<OrderRequestDTO> orderRequestDTOs);

    /***
     * Modifies local date time for already created order
     * @param orderId - order instance id
     * @param localDateTime - local date time working hours 08.00 - 24.00
     * @return orderResponseDTO - order response dto instance
     */
    OrderResponseDTO update(Long orderId, ZonedDateTime localDateTime);


    /***
     * Assigns order to the photographer
     * @param orderId -  order instance id
     * @param photographerId -  photographer instance id
     * @return orderResponseDTO - order response dto instance
     */
    OrderResponseDTO assign(Long orderId, Long photographerId);


    /***
     * Uploads photo to order
     * @param orderId - order instance id
     * @param zipFIle - zip of photos to upload to the system
     */
    OrderResponseDTO uploadPhoto(Long orderId, MultipartFile zipFIle);

    /***
     * Verifies the content of the zip files
     * @param orderId - order instance id
      */
    OrderResponseDTO verifyContent(Long orderId);

    /***
     * Removes order from database if exists
     * @param orderIds -  list of order instance ids
     */
    void cancel(List<Long>  orderIds);
}
