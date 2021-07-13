package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.service.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.OK;

@Validated
@Tag(name = "Order endpoints")
@RestController("/api/v1")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }

    @PatchMapping(value = "/order/{order_id}")
    @ResponseStatus(OK)
    public void update(@PathVariable("order_id") Long orderId, @RequestBody LocalDateTime localDateTime) {
        orderService.update(orderId, localDateTime);
    }

    @PostMapping("/order/{order_id}/photographer/{photographer_id}")
    @ResponseStatus(OK)
    public void assign(@PathVariable("order_id") Long orderId, @PathVariable("photographer_id") Long photographerId) {
        orderService.assign(orderId, photographerId);
    }

    @DeleteMapping("/order/{order_id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable("order_id") Long orderId) {
        orderService.cancel(orderId);
    }

    @PostMapping("/order/file/{order_id}")
    @ResponseStatus(OK)
    // TODO could then have Role PHOTOGRAPHER and ADMIN, who only can verify @RolesAllowed({"ROLE_ADMIN", "ROLE_PHOTOGRAPHER"})
    public void upload(@PathVariable("order_id") Long orderId, @RequestParam("zip_file") MultipartFile zipFile) {
        orderService.uploadPhoto(orderId, zipFile);
    }

    @PostMapping("/order/image/{order_id}")
    @ResponseStatus(OK)
    // TODO could then have Role OPERATOR and ADMIN, who only can verify @RolesAllowed({"ROLE_ADMIN", "ROLE_OPERATOR"})
    public void verify(@PathVariable("order_id") Long orderId) {
        orderService.verifyContent(orderId);
    }
}
