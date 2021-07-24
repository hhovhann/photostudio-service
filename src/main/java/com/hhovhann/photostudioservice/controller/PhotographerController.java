package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.service.PhotographerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Tag(name = "Photographer endpoints")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    @PostMapping("/v1/api/photographers")
    @ResponseStatus(CREATED)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PHOTOGRAPHER"})
    public List<Long> create(@Valid @RequestBody List<PhotographerRequestDTO> photographerRequestDTOs) {
        return photographerService.create(photographerRequestDTOs);
    }

    @DeleteMapping("/v1/api/photographers/{photographer_ids}")
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("ROLE_ADMIN")
    public void delete(@PathVariable("photographer_ids") List<Long> photographerIds) {
        photographerService.cancel(photographerIds);
    }
}
