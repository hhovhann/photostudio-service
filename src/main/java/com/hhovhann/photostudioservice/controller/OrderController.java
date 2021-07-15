package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.service.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@Tag(name = "Order endpoints")
@RestController
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/v1/api/order")
    @RolesAllowed("ROLE_ADMIN")
    @ResponseStatus(CREATED)
    public Long create(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }

    @PatchMapping(value = "/v1/api/order/{order_id}")
    @RolesAllowed("ROLE_ADMIN")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("order_id") Long orderId, @RequestBody LocalDateTime localDateTime) {
        orderService.update(orderId, localDateTime);
    }

    @PatchMapping("/v1/api/order/{order_id}/photographer/{photographer_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("ROLE_ADMIN")
    public void assign(@PathVariable("order_id") Long orderId, @PathVariable("photographer_id") Long photographerId) {
        orderService.assign(orderId, photographerId);
    }

    @DeleteMapping("/v1/api/order/{order_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("ROLE_ADMIN")
    public void delete(@PathVariable("order_id") Long orderId) {
        orderService.cancel(orderId);
    }

    @PatchMapping("/v1/api/order/file/{order_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PHOTOGRAPHER"})
    public void upload(@PathVariable("order_id") Long orderId, @RequestParam("zip_file") MultipartFile zipFile) {
        orderService.uploadPhoto(orderId, zipFile);
    }

    @PatchMapping("/v1/api/order/image/{order_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("ROLE_OPERATOR")
    public void verify(@PathVariable("order_id") Long orderId) {
        orderService.verifyContent(orderId);
    }
}
