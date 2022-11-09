package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.dto.PhotographerResponseDTO;
import com.hhovhann.photostudioservice.service.PhotographerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RolesAllowed("ROLE_ADMIN")
@Tag(name = "Photographer endpoints")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    @PostMapping("/v1/api/photographers")
    @ResponseStatus(CREATED)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PHOTOGRAPHER"})
    public ResponseEntity<List<Long>> create(@Valid @RequestBody List<PhotographerRequestDTO> photographerRequestDTOs) {
        return ResponseEntity.ok(photographerService.create(photographerRequestDTOs));
    }

    @PatchMapping("/v1/api//photographers/{photographer_id}")
    @ResponseStatus(OK)
    public ResponseEntity<PhotographerResponseDTO> update(@PathVariable("photographer_id") Long photographer_id, PhotographerRequestDTO photographerRequestDTO) {
        return ResponseEntity.ok(photographerService.update(photographer_id, photographerRequestDTO));
    }

    @DeleteMapping("/v1/api/photographers/{photographer_ids}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("photographer_ids") List<Long> photographerIds) {
        photographerService.cancel(photographerIds);
    }
}
