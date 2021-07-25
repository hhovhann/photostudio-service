package com.hhovhann.photostudioservice.mapper;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.dto.PhotographerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PhotographerMapper {

    public PhotographerEntity toEntity(PhotographerRequestDTO photographerRequestDTO) {
        PhotographerEntity photographerEntity = new PhotographerEntity();

        ContactData contactData = new ContactData();
        contactData.setName(photographerRequestDTO.getName());
        contactData.setSurname(photographerRequestDTO.getSurname());
        contactData.setCellNumber(photographerRequestDTO.getCellNumber());
        contactData.setEmail(photographerRequestDTO.getEmail());

        photographerEntity.setContactData(contactData);
        return photographerEntity;
    }

    public PhotographerResponseDTO toDTO(PhotographerEntity photographerEntity) {
        PhotographerResponseDTO photographerResponseDTO = new PhotographerResponseDTO();
        ContactData contactData = photographerEntity.getContactData();

        photographerResponseDTO.setId(photographerEntity.getId());
        photographerResponseDTO.setName(contactData.getName());
        photographerResponseDTO.setSurname(contactData.getSurname());
        photographerResponseDTO.setCellNumber(contactData.getCellNumber());
        photographerResponseDTO.setEmail(contactData.getEmail());
        return photographerResponseDTO;
    }
}
