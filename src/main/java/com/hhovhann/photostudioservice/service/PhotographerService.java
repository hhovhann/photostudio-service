package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;

public interface PhotographerService {
    Long create(PhotographerRequestDTO photographerRequestDTO);
}
