package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.dto.PhotographerResponseDTO;

import java.util.List;

public interface PhotographerService {
    /***
     * Creates a list of photographers
     * @param photographerRequestDTOs - photographers data transfer object
     */
    List<Long>  create(List<PhotographerRequestDTO> photographerRequestDTOs);

    void cancel(List<Long> photographerIds);

    PhotographerResponseDTO update(Long photographer_id, PhotographerRequestDTO photographerRequestDTO);
}
