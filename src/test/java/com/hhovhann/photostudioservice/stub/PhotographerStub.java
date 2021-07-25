package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;

public class PhotographerStub {

    public static PhotographerEntity createPhotographerEntity(long id) {
        PhotographerEntity photographerEntity = new PhotographerEntity();
        photographerEntity.setId(id);
        photographerEntity.setContactData(ContactDataStub.createOrderRequestDto(id));
        return photographerEntity;
    }

    public static PhotographerRequestDTO createPhotographerRequestDTO(long sequence) {
        PhotographerRequestDTO photographerRequestDTO = new PhotographerRequestDTO();
        photographerRequestDTO.setName("Photographer name " + sequence);
        photographerRequestDTO.setSurname("Photographer surname");
        photographerRequestDTO.setEmail("photographer" + sequence + "@email.com");
        photographerRequestDTO.setCellNumber("01234567890");
        return photographerRequestDTO;
    }
}
