package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.service.PhotographerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/api")
@Tag(name = "Photographer endpoints")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;

    }

    @PostMapping("/photographer")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody PhotographerRequestDTO photographerRequestDTO) {
        return photographerService.create(photographerRequestDTO);
    }

}
