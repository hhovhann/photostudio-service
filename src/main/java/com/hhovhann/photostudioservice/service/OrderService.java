package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface OrderService {

    /***
     * Create order with or without local date time
     * @param orderRequestDTO - requested domain object to persist
     * @return created order instance id
     */
    Long create(OrderRequestDTO orderRequestDTO);

    /***
     * Modifies local date time for already created order
     * @param orderId - order instance id
     * @param localDateTime - local date time working hours 08.00 - 24.00
     */
    void update(Long orderId, LocalDateTime localDateTime);


    /***
     * Assigns order to the photographer
     * @param orderId -  order instance id
     * @param photographerId -  photographer instance id
     */
    void assign(Long orderId, Long photographerId);


    /***
     * Uploads photo to order
     * @param orderId - order instance id
     * @param zipFIle - zip of photos to upload to the system
     */
    void uploadPhoto(Long orderId, MultipartFile zipFIle);

    /***
     * Verifies the content of the zip files
     * @param orderId - order instance id
     */
    void verifyContent(Long orderId, String photoUrl);

    /***
     * Removes order from database if exists
     * @param orderId -  order instance id
     */
    void cancel(Long orderId);

}
