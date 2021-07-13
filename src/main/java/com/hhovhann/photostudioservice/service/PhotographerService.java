package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;

import java.util.List;

public interface PhotographerService {
    /***
     * Creates a list of photographers
     * @param photographerRequestDTOs - photographers data transfer object
     */
    void create(List<PhotographerRequestDTO> photographerRequestDTOs);

}
