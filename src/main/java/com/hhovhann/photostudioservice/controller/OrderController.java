package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.OrderResponseDTO;
import com.hhovhann.photostudioservice.service.OrderService;
import com.hhovhann.photostudioservice.service.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@Tag(name = "Order endpoints")
@RolesAllowed("ROLE_ADMIN")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/v1/api/orders")
    @ResponseStatus(CREATED)
    public ResponseEntity<List<Long>> create(@Valid @RequestBody List<OrderRequestDTO> orderRequestDTOs) {
        return ResponseEntity.ok(orderService.create(orderRequestDTOs));
    }

    @PatchMapping(value = "/v1/api/orders/{order_id}")
    @ResponseStatus(OK)
    public ResponseEntity<OrderResponseDTO> update(@PathVariable("order_id") Long orderId, @RequestBody ZonedDateTime localDateTime) {
        return ResponseEntity.ok(orderService.update(orderId, localDateTime));
    }

    @PatchMapping("/v1/api/orders/{order_id}/photographers/{photographer_id}")
    @ResponseStatus(OK)
    public ResponseEntity<OrderResponseDTO> assign(@PathVariable("order_id") Long orderId, @PathVariable("photographer_id") Long photographerId) {
        return ResponseEntity.ok(orderService.assign(orderId, photographerId));
    }

    @PatchMapping("/v1/api/orders/file/{order_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PHOTOGRAPHER"})
    public void upload(@PathVariable("order_id") Long orderId, @RequestParam("zip_file") MultipartFile zipFile) {
        orderService.uploadPhoto(orderId, zipFile);
    }

    @PatchMapping("/v1/api/orders/image/{order_id}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_OPERATOR"})
    public void verify(@PathVariable("order_id") Long orderId) {
        orderService.verifyContent(orderId);
    }

    @DeleteMapping("/v1/api/orders/{order_ids}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("order_ids") List<Long> orderIds) {
        orderService.cancel(orderIds);
    }
}
