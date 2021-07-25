package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;

public class PhotographerStub {

    public static PhotographerEntity createPhotographerEntity() {
        PhotographerEntity photographerEntity = new PhotographerEntity();

        return photographerEntity;
    }

    public static PhotographerRequestDTO createPhotographerRequestDTO(long sequence) {
        PhotographerRequestDTO photographerRequestDTO = new PhotographerRequestDTO();
        return photographerRequestDTO;
    }
}
