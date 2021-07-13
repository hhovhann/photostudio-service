package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotographerServiceImpl implements PhotographerService {
    private final PhotographerRepository photographerRepository;

    public PhotographerServiceImpl(PhotographerRepository photographerRepository) {
        this.photographerRepository = photographerRepository;
    }

    @Override
    public Long create(PhotographerRequestDTO photographerRequestDTO) {

        PhotographerEntity firstPhotographer = new PhotographerEntity();
        ContactData firstContactData = new ContactData();
        firstContactData.setFirstName(photographerRequestDTO.getFirstName());
        firstContactData.setLastName(photographerRequestDTO.getLastName());
        firstContactData.setEmail(photographerRequestDTO.getEmail());
        firstContactData.setPhone(photographerRequestDTO.getEmail());
        firstPhotographer.setContactData(firstContactData);

        PhotographerEntity entity = photographerRepository.save(firstPhotographer);

        return entity.getId();
    }
}
