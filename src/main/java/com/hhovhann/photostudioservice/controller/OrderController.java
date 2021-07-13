package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController("/api")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

//    @RolesAllowed("ADMIN")
    @GetMapping("/order")
    public List<OrderEntity> findAll() {
        return orderService.findAll();
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }

    @PutMapping(value = "/order/{order_id}")
    @ResponseStatus(OK)
    public void update(@PathVariable("order_id") Long orderId, @RequestBody LocalDateTime localDateTime) {
        orderService.update(orderId, localDateTime);
    }

    @DeleteMapping(value = "/order/{order_id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable("order_id") Long orderId) {
        orderService.cancel(orderId);
    }

    @PostMapping("/order/{order_id}/photographer/{photographer_id}")
    @ResponseStatus(OK)
    public void assign(@PathVariable("order_id") Long orderId, @PathVariable("photographer_id") Long photographerId) {
        orderService.assign(orderId, photographerId);
    }

    @DeleteMapping("/order/{order_id}/photographer/{photographer_id}")
    @ResponseStatus(OK)
    public void unassign(@PathVariable("order_id") Long orderId, @PathVariable("photographer_id") Long photographerId) {
        orderService.unassign(orderId, photographerId);
    }

    @PostMapping("/order/photographer/{order_id}")
    @ResponseStatus(OK)
    public void upload(@PathVariable("order_id") Long orderId, @RequestBody String photoUrl) {
        orderService.uploadPhoto(orderId, photoUrl);
    }
}
